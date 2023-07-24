package de.derioo.inventoryframework;

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
                if (cancel)return;
                if (Bukkit.getPlayer("DeRio_") != null) {
                    Bukkit.getScheduler().runTaskLater(instance, () -> {
                       // cancel = false;
                    }, 20 * 5);
                    cancel = true;
                    try {
                        InventoryFramework.builder()
                                .setup("Test", 9*4, 5)
                                .provider(new InventoryProvider() {
                                    @Override
                                    public void init(Player player, InventoryContents contents) {
                                        contents.fillBorders(Material.DIRT);
                                        PageSystem pagination = contents.getPageSystem();
                                        int page = pagination.currentPage();
                                        contents.set(13, SmartItem.get(new ItemBuilder(Material.PAPER).setName("Current: " + page).toItemStack()));
                                        contents.set(contents.getItems().length - 10, SmartItem.get(new ItemBuilder(pagination.isLast() ? Material.BARRIER : Material.ARROW).setName("Next Page").toItemStack(),
                                                (e, item) -> {
                                                    if (pagination.isLast()) {
                                                        player.sendMessage("Du letzte");
                                                        return;
                                                    }
                                                    pagination.nextPage();
                                                }));
                                        contents.set(contents.getItems().length - 11, SmartItem.get(new ItemBuilder(pagination.isFirst() ? Material.BARRIER : Material.ARROW).setName("Previous Page").toItemStack(),
                                                (e, item) -> {
                                                    if (pagination.isFirst()) {
                                                        player.sendMessage("Du erste");
                                                        return;
                                                    }
                                                    pagination.previousPage();
                                                }));
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
