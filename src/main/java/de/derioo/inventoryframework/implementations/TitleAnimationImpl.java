package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryAnimation;
import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.TitleAnimation;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class of the title animation interface
 */
public class TitleAnimationImpl implements TitleAnimation {

    private final InventoryBuilder builder;
    private final Plugin plugin;
    private AnimationType type;

    /**
     * Used to get an instance of the class
     *
     * @param builder the builder
     * @param plugin  the main plugin
     */
    public TitleAnimationImpl(InventoryBuilder builder, Plugin plugin) {
        this.builder = builder;
        this.plugin = plugin;
    }

    @Override
    public TitleAnimation prepare(AnimationType type) {
        this.type = type;
        return this;
    }

    @Override
    public TitleAnimation start(long delayBetweenTextChanges, InventoryAnimation.TimeUnit unit, String... inputs) {
        Component[] components = new Component[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            components[i] = Component.text(inputs[i]);
        }
        return this.start(delayBetweenTextChanges, unit, components);
    }

    @Override
    public TitleAnimation start(long delayBetweenTextChanges, InventoryAnimation.TimeUnit unit, Component... inputs) {
        List<Component> list = new ArrayList<>(Arrays.asList(inputs));
        long ticksBetweenChanges = this.getTicksFromTimeUnit(delayBetweenTextChanges, unit);


        new BukkitRunnable() {

            Inventory inventory = builder.getInventory();
            int currentCycle = 0;

            @Override
            public void run() {
                switch (type) {
                    case CYCLE_ONCE -> {
                        if (currentCycle == list.size()) {
                            this.cancel();
                            return;
                        }
                        inventory = builder.getContents().changeTitle(list.get(currentCycle));
                    }
                    case CYCLE_INFINITE -> {
                        if (currentCycle == list.size()) {
                            currentCycle = 1;
                            inventory = builder.getContents().changeTitle(inputs[0]);
                            return;
                        }
                        inventory = builder.getContents().changeTitle(inputs[currentCycle]);
                    }
                    case CYCLE_INFINITE_BACK_AND_FRONT -> {
                        if (currentCycle == list.size()) {
                            currentCycle = 1;
                            Collections.reverse(list);
                            inventory = builder.getContents().changeTitle(list.get(0));
                            return;
                        }
                        inventory = builder.getContents().changeTitle(list.get(currentCycle));
                    }
                }
                currentCycle++;
            }
        }.runTaskTimer(plugin, ticksBetweenChanges, ticksBetweenChanges);

        return this;
    }


    private long getTicksFromTimeUnit(long time, InventoryAnimation.TimeUnit unit) {
        long ticks;
        switch (unit) {
            case MINUTES -> ticks = time * 60 * 20;
            case SECONDS -> ticks = time * 20;
            case MILLISECONDS -> ticks = time / 50;
            case TICKS -> ticks = time;
            default -> ticks = -1;
        }
        return ticks;
    }
}
