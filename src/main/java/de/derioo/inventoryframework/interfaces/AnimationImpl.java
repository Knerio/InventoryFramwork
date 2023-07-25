package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AnimationImpl implements Animation {

    private final InventoryBuilder builder;
    private final Plugin plugin;
    private AnimationType type;

    private boolean hideAfterAnimation = false;

    public AnimationImpl(InventoryBuilder builder, Plugin plugin) {
        this.builder = builder;
        this.plugin = plugin;
    }

    @Override
    public Animation prepare(AnimationType t) {
        this.type = t;
        return this;
    }

    @Override
    public Animation hideAfterAnimation() {
        hideAfterAnimation = true;
        return this;
    }

    @Override
    public Animation start(long delayBetweenItems, TimeUnit unit, AnimationItem... items) {
        long delayBetweenItemsInTicks = getTicksFromTimeUnit(delayBetweenItems, unit);

        InventoryContents contents = builder.getContents();


        final Map<Integer, SmartItem> map = new HashMap<>();

        for (int k = 0; k < contents.getItems().length; k++) {

            if (contents.getItem(k) == null) {
                map.put(k, SmartItem.get(new ItemStack(Material.AIR)));
                continue;
            }
            map.put(k, contents.getItem(k));
        }
        new BukkitRunnable() {
            int displayedItems = 0;

            @Override
            public void run() {
                SmartItem[] newContents = contents.getItems();

                //first load the map in the array
                for (AnimationItem item : items) {
                    newContents[item.getStartSlot()] = map.get(item.getStartSlot());
                    newContents[item.getCurrentSlot()] = map.get(item.getCurrentSlot());
                }

                //Renders the next slots
                for (int i = 0; i < displayedItems; i++) {
                    AnimationItem item = items[i];
                    int nextSlot = getNextSlotByType(item.getCurrentSlot(), type, displayedItems + 1);
                    System.out.println(nextSlot);
                    System.out.println(item.getStepCount());
                    if (item.getStepCount() > item.getAnimationLength()) continue;
                    item.setCurrentSlot(nextSlot);
                    item.setStepCount(item.getStepCount()+1);
                    newContents[item.getCurrentSlot()] = item.getItem();
                }

                //Checks if the next rendered item can or has to be rendered
                if (displayedItems < items.length) {
                    newContents[items[displayedItems].getStartSlot()] = items[displayedItems].getItem();
                    displayedItems++;
                }

                //Checks if the runnable has to be cancelled
                AnimationItem lastItem = items[items.length - 1];
                if (lastItem.getStepCount() == lastItem.getAnimationLength() + 1) {
                    this.cancel();
                    if (hideAfterAnimation)
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            for (AnimationItem item : items) {
                                newContents[item.getStartSlot()] = map.get(item.getStartSlot());
                                newContents[item.getCurrentSlot()] = map.get(item.getCurrentSlot());
                                System.out.println(item.getCurrentSlot());
                            }
                            contents.update();
                        }, delayBetweenItemsInTicks);
                    contents.update();
                }

                contents.setItems(newContents);
                contents.update();


            }
        }.runTaskTimer(plugin, delayBetweenItemsInTicks, delayBetweenItemsInTicks);



        return this;

    }

    @Override
    public Animation start(long delayBetweenItems, TimeUnit unit, List<AnimationItem> items) {
        AnimationItem[] array = new AnimationItem[items.size()];

        for (int i = 0; i < items.size(); i++) {
            array[i] = items.get(i);
        }

        start(delayBetweenItems, unit, array);
        return this;
    }


    private long getTicksFromTimeUnit(long time, TimeUnit unit) {
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


    private int getNextSlotByType(int slot, AnimationType type, int cycle) {
        switch (type) {
            case VERTICAL_UP_DOWN -> {
                return slot + 9;
            }
            case VERTICAL_DOWN_UP -> {
                return slot - 9;
            }
            case HORIZONTAL_LEFT_RIGHT -> {
                return slot + 1;
            }
            case HORIZONTAL_RIGHT_LEFT -> {
                return slot - 1;
            }
            case CROSS_LEFT_UP_RIGHT_DOWN -> {
                return slot + 10;
            }
            case CROSS_RIGHT_UP_LEFT_DOWN -> {
                return slot - 10;
            }
            default -> {
                return -1;
            }
        }

    }

}
