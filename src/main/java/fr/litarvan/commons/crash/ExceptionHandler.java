/*
 * Copyright 2017 Adrien "Litarvan" Navratil
 *
 * This file is part of Litarvan Commons.
 *
 * Litarvan Common is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Litarvan Common is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Litarvan Common.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.litarvan.commons.crash;

import fr.litarvan.commons.App;
import fr.litarvan.commons.Canceller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * The Exception Handler<br><br>
 *
 *
 * Powerful exception handling system. It creates crash
 * report as it handle exceptions, and is Guice compatible
 * as it is registered Singleton. It can trigger events
 * also.<br><br>
 *
 * Example :
 * <pre>
 *     ExceptionHandler handler = new ExceptionHandler(myApp);
 *
 *     handler.trigger((handler, throwable, cancel) -> {
 *         // Will be called when an exception is caught
 *         cancel.cancel(); // Cancel the crash report creation
 *     });
 *
 *     handler.on(MyException.class, (handler, throwable, cancel) -> {
 *         // Will be called when a MyException is caugth
 *     });
 *
 *     MyValue result = handler.handler(() -> {
 *         someRiskyThings();
 *         return riskyOperation();
 *     }); // result is null if an exception was triggered
 *
 *     try
 *     {
 *         someRiskyThings();
 *     }
 *     catch (MyException ex)
 *     {
 *         handler.handle(ex); // Manual handling
 *     }
 * </pre>
 *
 * By default the crash reports are saved to the app root folder,
 * and no report is generated if the folder is null.<br><br>
 *
 * A crash report looks like that :
 * <pre>
 *     ###########################################
 *
 *     MyApp v2.0 crash report
 *
 *     Version : 2.0.0
 *     Time : 02/05/17 12:30
 *     Exception : [MyException] My exception message
 *
 *     ======>
 *
 *     my.app.MyException : My exception message
 *         at my.app.Thing.riskyOperation(Thing.java:67)
 *         at ...
 *
 *     ###########################################
 * </pre>
 *
 * You can add your own report fields (like Version, Time, Exception)
 * using the {@link #addField(IReportField)} method.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
@Singleton
public class ExceptionHandler
{
    private App app;
    private File crashFolder;
    private List<IReportField> fields;
    private List<ExceptionTrigger> triggers;
    private Map<Class<? extends Throwable>, ExceptionTrigger> specificTriggers;

    @Inject
    public ExceptionHandler(App app)
    {
        this(app, app.getFolder() == null ? null : new File(app.getFolder(), "crashes"));
    }

    public ExceptionHandler(App app, File crashFolder)
    {
        this.app = app;
        this.crashFolder = crashFolder;

        this.fields = new ArrayList<>();
        this.triggers = new ArrayList<>();
        this.specificTriggers = new HashMap<>();

        this.addField(new ConstantReportField("Version", app.getVersion()))
            .addField(new ReportField("Time", (handler, t) -> new SimpleDateFormat().format(new Date())))
            .addField(new ReportField("Exception", (handler, t) -> "[" + t.getClass() + "] " + t.getMessage()));
    }

    public ExceptionHandler trigger(ExceptionTrigger trigger)
    {
        this.triggers.add(trigger);
        return this;
    }

    public ExceptionHandler on(Class<? extends Throwable> type, ExceptionTrigger trigger)
    {
        this.specificTriggers.put(type, trigger);
        return this;
    }

    public ExceptionHandler addField(IReportField field)
    {
        this.fields.add(field);
        return this;
    }

    public <T> T handler(Callable<T> callable)
    {
        return handler(callable, null);
    }

    public <T> T handler(Callable<T> callable, T def)
    {
        try
        {
            return callable.call();
        }
        catch (Throwable t)
        {
            handle(t);
        }

        return def;
    }

    public void handle(Throwable throwable)
    {
        if (!Canceller.chain(c -> {
            triggers.forEach(trigger -> trigger.trigger(this, throwable, c));
            specificTriggers.entrySet().stream()
                            .filter(entry -> entry.getKey() == throwable.getClass())
                            .forEach(entry -> entry.getValue().trigger(this, throwable, c));
        }))
        {
            return;
        }

        String report = makeCrashReport(throwable);

        System.err.println("##! Exception caught !##\n");
        System.err.println(report);

        if (crashFolder != null)
        {
            File file = new File(crashFolder, "crash-" + System.currentTimeMillis());
            System.err.println("=> Saving crash report to " + file.getAbsolutePath());
        }
    }

    protected String makeCrashReport(Throwable throwable)
    {
        StringBuilder result = new StringBuilder();

        result.append("\n###########################################\n\n");

        result.append(app.getName()).append(" v").append(app.getVersion()).append(" crash report\n\n");

        int keyMaxLength = 9;
        for (IReportField field : fields)
        {
            int l = field.getKey().length();
            keyMaxLength = l > keyMaxLength ? l : keyMaxLength;
        }

        StringBuilder spacing = new StringBuilder(" ");
        for (int i = 0; i < keyMaxLength; i++)
        {
            spacing.append(" ");
        }

        fields.forEach(field -> result.append(field.getKey()).append(spacing).append(": ").append(field.generateValue(this, throwable)).append("\n"));

        result.append("\n======>\n\n");

        result.append(ExceptionUtils.getStackTrace(throwable)).append("\n\n");

        result.append("###########################################\n\n");

        return result.toString();
    }

    public App getApp()
    {
        return app;
    }

    public File getCrashFolder()
    {
        return crashFolder;
    }

    public List<IReportField> getFields()
    {
        return fields;
    }

    public List<ExceptionTrigger> getTriggers()
    {
        return triggers;
    }
}
