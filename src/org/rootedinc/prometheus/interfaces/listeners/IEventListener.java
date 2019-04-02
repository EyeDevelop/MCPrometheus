package org.rootedinc.prometheus.interfaces.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public interface IEventListener extends Listener {

    Class<Event> getEventType();

    EventPriority getEventPriority();

    EventExecutor getEventExecutor();

    void onReload();

    boolean isAdvanced();

}
