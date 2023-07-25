package de.derioo.inventoryframework;

import de.derioo.inventoryframework.interfaces.Animation;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.InventoryProvider;
import de.derioo.inventoryframework.interfaces.PageSystem;
import de.derioo.inventoryframework.objects.InventoryFramework;
import de.derioo.inventoryframework.objects.SmartItem;
import de.derioo.inventoryframework.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().log(Level.WARNING, "Inventory Framework cannot be enabled");
        new InventoryFramework(this);

        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            boolean cancel = false;

            @Override
            public void run() {
                if (cancel) return;
                if (Bukkit.getPlayer("DeRio_") != null) {
                    Bukkit.getScheduler().runTaskLater(instance, () -> {
                        // cancel = false;
                    }, 20 * 5);
                    cancel = true;
                    try {
                        InventoryFramework.builder()
                                .setup("Test", 54, 5)
                                .provider(new InventoryProvider() {
                                    @Override
                                    public void init(Player player, InventoryContents contents) {
                                        contents.fillBorders(Material.DIRT);

                                        List<Animation.AnimationItem> list = new ArrayList<>();
                                        Random random = new Random();
                                        for (int i = 0; i < 100; i++) {
                                            Animation.AnimationItem item = new Animation.AnimationItem(
                                                    SmartItem.get(new ItemBuilder(Material.values()[random.nextInt(Material.values().length - 1)]).toItemStack()),
                                                    3, 52);
                                            list.add(item);
                                        }

                                        InventoryFramework.animation(contents.getBuilder())
                                                .prepare(Animation.AnimationType.CROSS_RIGHT_UP_LEFT_DOWN)
                                                .hideAfterAnimation()
                                                .start(140, Animation.TimeUnit.MILLISECONDS, list);

                                    }
                                })
                                .build()
                                .open(Bukkit.getPlayer("DeRio_"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }, 20, 20);
    }

}
