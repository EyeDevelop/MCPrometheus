package org.rootedinc.prometheus;

import org.bukkit.plugin.java.JavaPlugin;
import org.rootedinc.prometheus.interfaces.listeners.EventListener;
import org.rootedinc.prometheus.interfaces.listeners.ListenerManager;
import org.rootedinc.prometheus.listeners.EntityListener;
import org.rootedinc.prometheus.listeners.SignListener;

/**
 * The Listener Manager for Prometheus.
 */
class LstnMgrPrometheus extends ListenerManager {

    /**
     * Constructor for the ListenManager.
     *
     * @param pluginParent The JavaPlugin parent of this Plugin Manager.
     */
    LstnMgrPrometheus(JavaPlugin pluginParent) {
        super(pluginParent);

        // Register the listeners.
        EventListener playerListener = new EntityListener();
        registerListener(playerListener);

        EventListener signListener = new SignListener();
        registerListener(signListener);
    }

}
