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
package fr.litarvan.commons.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Internal file jar source<br><br>
 *
 *
 * A file source from a file in the classpath.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class InternalFileSource implements IOSource
{
    private String path;

    /**
     * The internal file source
     *
     * @param path The path of the resource
     */
    public InternalFileSource(String path)
    {
        this.path = "/" + (path.startsWith("/") ? path.substring(1) : path);
    }

    @Override
    public InputStream provideInput()
    {
        return getClass().getResourceAsStream(path);
    }

    @Override
    public OutputStream provideOutput()
    {
        throw new UnsupportedOperationException("Cannot write to internal file source");
    }

    @Override
    public boolean exists()
    {
        return getClass().getResource(path) != null;
    }

    /**
     * @return The path of the resource
     */
    public String getPath()
    {
        return path;
    }
}
