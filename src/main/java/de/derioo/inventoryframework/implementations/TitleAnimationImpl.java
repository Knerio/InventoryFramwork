package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryAnimation;
import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.TitleAnimation;
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

public class TitleAnimationImpl implements TitleAnimation {

    private final InventoryBuilder builder;
    private final Plugin plugin;
    private AnimationType type;


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
        List<String> list = new ArrayList<>();
        for (String input : inputs) {
            list.add(input);
        }
        long ticksBetweenChanges = getTicksFromTimeUnit(delayBetweenTextChanges, unit);
        InventoryContents contents = builder.getContents();


        new BukkitRunnable() {

            Inventory inventory = builder.getInventory();
            int currentCycle = 0 ;
            @Override
            public void run() {
                switch (type){
                    case CYCLE_ONCE -> {
                        if (currentCycle == list.size()){
                            this.cancel();
                            return;
                        }
                        inventory = changeTitle(inventory, list.get(currentCycle));
                        builder.setInventory(inventory);
                    }
                    case CYCLE_INFINITE -> {
                        if (currentCycle == list.size()){
                            currentCycle = 1;
                            inventory = changeTitle(inventory, inputs[0]);
                            return;
                        }
                        inventory = changeTitle(inventory, inputs[currentCycle]);
                        builder.setInventory(inventory);
                    }
                    case CYCLE_INFINITE_BACK_AND_FRONT -> {
                        if (currentCycle == list.size()){
                            currentCycle = 1;
                            Collections.reverse(list);
                            inventory = changeTitle(inventory, list.get(0));
                            return;
                        }
                        inventory = changeTitle(inventory, list.get(currentCycle));
                        builder.setInventory(inventory);
                    }
                }
                currentCycle++;
            }
        }.runTaskTimer(plugin, ticksBetweenChanges, ticksBetweenChanges);

        return this;
    }

    private Inventory changeTitle(Inventory inventory, String newTitle){
        int size = inventory.getSize();

        Inventory newInventory = Bukkit.createInventory((InventoryHolder) inventory.getHolder(), size, newTitle);

        newInventory.setContents(inventory.getContents());


        for (HumanEntity viewer : new ArrayList<>(inventory.getViewers())) {
            viewer.openInventory(newInventory);
        }

        // Das neue Inventar zurückgeben
        return newInventory;
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
