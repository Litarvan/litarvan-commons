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

import java.util.function.BiFunction;

/**
 * Report field<br><br>
 *
 *
 * A simple report field with a constant key and a BiFunction
 * to generate the value.<br><br>
 *
 * Example :
 * <pre>
 *     handler.addField(new ReportField("Time", (handler, throwable) -> new Date().toString());
 * </pre>
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReportField implements IReportField
{
    private String key;
    private BiFunction<ExceptionHandler, Throwable, String> valueGenerator;

    /**
     * The field
     *
     * @param key The field key
     * @param valueGenerator The field value generator
     */
    public ReportField(String key, BiFunction<ExceptionHandler, Throwable, String> valueGenerator)
    {
        this.key = key;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public String getKey()
    {
        return this.key;
    }

    @Override
    public String generateValue(ExceptionHandler handler, Throwable t)
    {
        return valueGenerator.apply(handler, t);
    }
}
