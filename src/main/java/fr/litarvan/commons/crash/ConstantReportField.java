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

/**
 * Constant report field<br><br>
 *
 *
 * A crash report field with a constant key and a constant
 * value.<br>
 * Example : The application version
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class ConstantReportField implements IReportField
{
    private String key;
    private String value;

    /**
     * The field
     *
     * @param key The field key
     * @param value The field value
     */
    public ConstantReportField(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public String generateValue(ExceptionHandler handler, Throwable t)
    {
        return value;
    }
}
