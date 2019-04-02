package org.rootedinc.prometheus.interfaces.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;

/**
 * Custom implementation of Bukkit's Listener to make listener registering easier.
 */
public abstract class EventListener implements IEventListener {

    private Class<Event> eventType;
    private EventPriority eventPriority;
    private EventExecutor eventExecutor;
    private boolean isAdvanced = false;


    /**
     * @param listenerManager The ListenerManager of the Plugin where this listener is about to be registered.
     */
    public EventListener(IListenerManager listenerManager) {
        listenerManager.registerListener(this);
    }

    /**
     * @param eventType       The type of event. I.e. PlayerJoinEvent.
     * @param eventPriority   The priority of the event.
     * @param eventExecutor   The actual executor of this event.
     * @param listenerManager The ListenerManager of the Plugin where this listener is about to be registered.
     */
    public EventListener(Class<Event> eventType, EventPriority eventPriority, EventExecutor eventExecutor, IListenerManager listenerManager) {
        this.eventType = eventType;
        this.eventPriority = eventPriority;
        this.eventExecutor = eventExecutor;

        this.isAdvanced = true;
        listenerManager.registerListener(this);
    }

    /**
     * @return This event's type.
     */
    @Override
    public Class<Event> getEventType() {
        return eventType;
    }

    /**
     * @return This event's priority.
     */
    @Override
    public EventPriority getEventPriority() {
        return eventPriority;
    }

    /**
     * @return This event's executor.
     */
    @Override
    public EventExecutor getEventExecutor() {
        return eventExecutor;
    }

    /**
     * Function to be ran when the plugin is reloaded.
     */
    @Override
    public abstract void onReload();

    /**
     * @return Whether this event is advanced (custom type, priority, etc.)
     */
    @Override
    public boolean isAdvanced() {
        return isAdvanced;
    }
}
