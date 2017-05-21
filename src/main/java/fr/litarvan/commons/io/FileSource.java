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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File IO Source<br><br>
 *
 *
 * This is a IO Source of a file.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileSource implements IOSource
{
    private File file;

    /**
     * The File Source
     *
     * @param file The file to read/write
     */
    public FileSource(File file)
    {
        this.file = file;
    }

    @Override
    public InputStream provideInput()
    {
        try
        {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("Cannot read from " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public OutputStream provideOutput()
    {
        try
        {
            if (!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }

            return new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("Cannot write in " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public boolean exists()
    {
        return file.exists();
    }

    /**
     * @return The file
     */
    public File getFile()
    {
        return file;
    }
}
