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
package fr.litarvan.commons;

import java.io.File;
import javax.inject.Singleton;

/**
 * An Application interface<br><br>
 *
 *
 * This interface is used to define simple applications
 * classes with a name and a version. It supports Guice
 * as it is annotated Singleton.<br><br>
 *
 * You can also provide a folder for the ExceptionHandler
 * and the ConfigProvider.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface App
{
    /**
     * @return The application name
     */
    String getName();

    /**
     * @return The application version
     */
    String getVersion();

    /**
     * @return The app storage folder
     */
    default File getFolder()
    {
        return null;
    }

    /**
     * Starts the application
     */
    void start();
}
