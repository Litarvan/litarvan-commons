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

import java.util.function.Consumer;

/**
 * The Canceller<br><br>
 *
 *
 * The Canceller is an object that can be given to events
 * so they can trigger it to cancel the event.<br>
 * You can use the {@link #chain(Consumer)} method to
 * make it automatic.<br><br>
 *
 * Example :
 * <pre>
 *     public void onEvent(Canceller canceller)
 *     {
           // Do things
 *         canceller.cancel();
 *     }
 *
 *
 *     private List&lt;MyEventListener&gt myListeners;
 *     ...
 *     if (!Canceller.chain(c -> myListeners.forEach(l -> l.onEvent(c))
 *     {
 *         // Will trigger if any listener called {@link #cancel()}
 *         return;
 *     }
 *
 *     // Continue event
 * </pre>
 *
 * @author Litarvan
 * @version 1.0.0
 * @since 1.0.0
 */
public class Canceller
{
    private boolean cancelled;

    /**
     * The canceller.<br>
     * By default, it is not cancelled.
     */
    public Canceller()
    {
        this.cancelled = false;
    }

    /**
     * Cancel the current event
     */
    public void cancel()
    {
        this.cancelled = true;
    }

    /**
     * @return If the event was cancelled
     */
    public boolean isCancelled()
    {
        return cancelled;
    }

    /**
     * Create a canceller chain. It creates a canceller, gives it to
     * a Consumer, and then returns false if it is cancelled.<br><br>
     *
     * Example :
     * <pre>
     *     if (!Canceller.chain(c -> listeners.forEach(listener -> listener.onEvent(c)))
     *     {
     *         // Will trigger if an event called {@link #cancel()}
     *         return;
     *     }
     *
     *     // Continue event
     * </pre>
     *
     * @param action The action to call with the created canceller
     *
     * @return False if {@link #cancel()} was called, true if not
     */
    public static boolean chain(Consumer<Canceller> action)
    {
        Canceller canceller = new Canceller();
        action.accept(canceller);

        return canceller.isCancelled();
    }
}
