package org.rootedinc.prometheus.interfaces.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public interface IEventListener extends Listener {

    /**
     * [ADVANCED] Function to retrieve event type.
     *
     * @return This event's type.
     */
    Class<Event> getEventType();

    /**
     * [ADVANCED] Function to retrieve event priority.
     *
     * @return This event's priority.
     */
    EventPriority getEventPriority();

    /**
     * [ADVANCED] Function to retrieve the event executor.
     *
     * @return This event's executor.
     */
    EventExecutor getEventExecutor();

    /**
     * Function to be ran when the plugin is reloaded.
     */
    void onReload();

    /**
     * Retrieves whether the event is configured in an advanced manner.
     *
     * @return Whether this event is advanced (custom type, priority, etc.)
     */
    boolean isAdvanced();

}
