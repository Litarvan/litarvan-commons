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

/**
 * The File Config<br><br>
 *
 *
 * A config that can be loaded/saved from/to a file.
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class FileConfig implements Config
{
    /**
     * If it should save automatically when defining a value
     */
    protected boolean autoSave = true;

    /**
     * The config file
     */
    protected IOSource file;

    /**
     * Empty config, no file set, can't save until set.
     */
    public FileConfig()
    {
    }

    /**
     * Config from a file, if it exists, config will be loaded from it.
     *
     * @param file The file of the config
     */
    public FileConfig(File file)
    {
        this(IOSource.file(file));
    }

    /**
     * Config from a source, if it exists, config will be loaded from it.
     *
     * @param source The source of the config
     */
    public FileConfig(IOSource source)
    {
        this.in(source);

        if (source != null && source.exists())
        {
            this.load();
        }
    }

    /**
     * @return The source of the config
     */
    public IOSource getFile()
    {
        return file;
    }

    /**
     * Define the file of the config
     *
     * @param file The config file
     *
     * @return This
     */
    public FileConfig in(File file)
    {
        return in(IOSource.file(file));
    }

    /**
     * Define the file of the config
     *
     * @param file The config file
     *
     * @return This
     */
    public FileConfig in(IOSource file)
    {
        this.file = file;
        return this;
    }

    /**
     * Enable or disable automatic saving when setting a value
     *
     * @param autoSave Enable/disable the auto save
     *
     * @return This
     */
    public FileConfig autoSave(boolean autoSave)
    {
        this.autoSave = autoSave;
        return this;
    }

    /**
     * @return If it automatically saves when setting a value
     */
    public boolean isAutoSaveEnabled()
    {
        return autoSave;
    }

    /**
     * @return If saving is supported
     */
    @Override
    public boolean isSavingSupported()
    {
        return true;
    }

    /**
     * Load the config from the file.<br>
     * Will probably throw an exception if no file is set.
     *
     * @return This
     */
    public abstract FileConfig load();

    /**
     * Save the config from the file.<br>
     * Will probably throw an exception if no file is set or
     * saving is not supported.
     *
     * @return This
     */
    public abstract FileConfig save();

    /**
     * Provide a default configuration from a file
     *
     * @param file The default configuration
     *
     * @return This
     */
    public FileConfig defaultIn(File file)
    {
        return defaultIn(IOSource.file(file));
    }

    /**
     * Provide a default configuration
     *
     * @param source The source of the default configuration
     *
     * @return This
     */
    public abstract FileConfig defaultIn(IOSource source);
}
