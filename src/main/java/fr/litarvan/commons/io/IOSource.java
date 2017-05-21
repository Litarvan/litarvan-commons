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

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO Source<br><br>
 *
 *
 * A source that can provide an InputStream and an OutputStream.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IOSource
{
    /**
     * @return Provide a stream to read the file
     */
    InputStream provideInput();

    /**
     * @return Provide a stream to write the file
     */
    OutputStream provideOutput();

    /**
     * @return If the source exists
     */
    boolean exists();

    /**
     * Create a FileSource from a File path
     *
     * @param file The path of the file to read
     *
     * @return The generated IOSource
     */
    static FileSource file(String file)
    {
        return file(new File(file));
    }

    /**
     * Create a FileSource from a File object
     *
     * @param file The file to read
     *
     * @return The generated IOSource
     */
    static FileSource file(File file)
    {
        return new FileSource(file);
    }

    /**
     * Create a FileSource from a path. If the path
     * is an existing internal resource it will load it,
     * or then it will be a file.
     *
     * @param path The path of the resource
     *
     * @return The generated IOSource
     */
    static IOSource at(String path)
    {
        IOSource source = internal(path);

        if (source.exists())
        {
            return source;
        }
        else
        {
            return file(path);
        }
    }

    /**
     * Create a FileSource from a jar resource
     *
     * @param path The path (example : /fr/litarvan/resources/myresource.png)
     *             of the file in the jar
     *
     * @return The created IOSource
     */
    static InternalFileSource internal(String path)
    {
        return new InternalFileSource(path);
    }
}
