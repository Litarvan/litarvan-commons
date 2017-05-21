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
package fr.litarvan.commons.config;

import fr.litarvan.commons.io.IOSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * The Properties Config<br><br>
 *
 *
 * A Config made using Java Properties.<br>
 * To create one, consider using the {@link ConfigProvider#properties}
 * methods.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class PropertiesConfig extends FileConfig
{
    private Properties properties = new Properties();

    public PropertiesConfig()
    {
    }

    public PropertiesConfig(File file)
    {
        super(file);
    }

    @Override
    public String get(String key, String def)
    {
        return properties.getProperty(key, def);
    }

    @Override
    public void set(String key, String value)
    {
        properties.setProperty(key, value);

        if (autoSave)
        {
            save();
        }
    }

    @Override
    public boolean areObjectsSupported()
    {
        return false;
    }

    @Override
    public FileConfig load()
    {
        if (file == null)
        {
            throw new IllegalStateException("Config file isn't defined");
        }

        try
        {
            properties.load(file.provideInput());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can't read config", e);
        }

        return this;
    }

    @Override
    public FileConfig save()
    {
        try
        {
            properties.store(file.provideOutput(), "Krobot generated config\n");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can't save config", e);
        }

        return this;
    }

    @Override
    public FileConfig defaultIn(IOSource source)
    {
        if (file.exists())
        {
            return this;
        }

        if (!source.exists())
        {
            throw new RuntimeException("Default file doesn't exist");
        }

        try
        {
            properties.load(source.provideInput());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can't read default config", e);
        }

        save();

        return this;
    }
}
