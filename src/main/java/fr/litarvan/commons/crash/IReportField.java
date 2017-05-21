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
 * Crash report field<br><br>
 *
 *
 * A crash report field is an information in a crash report.<br>
 * Example :
 *
 * <pre>
 *     handler.addField(new ReportField("Type", (handler, throwable) -&gt; throwable.getClass().getSimpleName());
 *
 *     #############################################
 *
 *     MyApp v2.0.0 crash report
 *
 *     Type    : RuntimeException
 *     ...
 *
 *     java.lang.RuntimeException : ...
 *         at ....
 *
 *     #############################################
 * </pre>
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IReportField
{
    /**
     * The key of the report field (the part before the : )
     * @return The field key
     */
    String getKey();

    /**
     * Generate the field value (part after the : )
     *
     * @param handler The handler that is generating the report
     * @param t The throwable of the report
     *
     * @return The created value
     */
    String generateValue(ExceptionHandler handler, Throwable t);
}
