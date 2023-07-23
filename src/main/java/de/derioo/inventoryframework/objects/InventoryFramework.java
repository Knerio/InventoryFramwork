package de.derioo.inventoryframework.objects;

import de.derioo.inventoryframework.exeptions.FrameworkNotConfiguredException;
import de.derioo.inventoryframework.implementations.InventoryBuilderImpl;
import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class InventoryFramework {

    @Getter
    private static Plugin plugin;

    /**
     * Has to be initialized once
     * @param main the plugin
     */
    public InventoryFramework(Plugin main){
        plugin = main;
    }


    /**
     * Used to init Listeners
     * @param listener the listener
     */
    public static void initListener(Listener listener) {
        if (plugin == null) throw new FrameworkNotConfiguredException();

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }


    public static InventoryBuilder builder(){
        return new InventoryBuilderImpl();
    }

}
