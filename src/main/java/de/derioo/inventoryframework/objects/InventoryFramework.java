package de.derioo.inventoryframework.objects;

import de.derioo.inventoryframework.exeptions.FrameworkNotConfiguredException;
import de.derioo.inventoryframework.implementations.InventoryBuilderImpl;
import de.derioo.inventoryframework.implementations.TitleAnimationImpl;
import de.derioo.inventoryframework.interfaces.InventoryAnimation;
import de.derioo.inventoryframework.implementations.InventoryAnimationImpl;
import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.TitleAnimation;
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
        if (plugin == null) throw new FrameworkNotConfiguredException("FrameWork is not configured use new InventoryFrameWork(plugin)");

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }


    public static InventoryBuilder builder(){
        return new InventoryBuilderImpl();
    }

    /**
     * Used to create a inventory animation
     * @param builder the builder of the animation
     * @return the Animation to use
     */
    public static InventoryAnimation invAnimation(InventoryBuilder builder){
        return new InventoryAnimationImpl(builder, plugin);
    }

    /**
     * Used to create a title animation
     * @param builder the builder of the animation
     * @return the Animation to use
     */
    public static TitleAnimation titleAnimation(InventoryBuilder builder){
        return new TitleAnimationImpl(builder, plugin);
    }

}
