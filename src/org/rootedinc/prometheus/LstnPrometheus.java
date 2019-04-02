package org.rootedinc.prometheus;

import org.bukkit.plugin.java.JavaPlugin;
import org.rootedinc.prometheus.interfaces.listeners.ListenerManager;
import org.rootedinc.prometheus.listeners.PlayerListener;

/**
 * The Listener Manager for Prometheus.
 */
class LstnPrometheus extends ListenerManager {

    // Register the listeners.
    PlayerListener playerListener = new PlayerListener(this);

    /**
     * @param pluginParent The JavaPlugin parent of this Plugin Manager.
     */
    LstnPrometheus(JavaPlugin pluginParent) {
        super(pluginParent);
    }

}
