package de.derioo.inventoryframework;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * The main class of the inner plugin, cannot be started
 */
public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.WARNING, "Inventory Framework cannot be enabled");
        Bukkit.getPluginManager().disablePlugin(this);
    }

}
