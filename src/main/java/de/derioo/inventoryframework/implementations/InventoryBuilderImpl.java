package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.InventoryProvider;
import de.derioo.inventoryframework.objects.InventoryFramework;
import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryBuilderImpl implements InventoryBuilder, Listener {

    private String title = "NOT_CONFIGURED";
    private int size = 9;
    private int maxPage = -1;
    private InventoryProvider provider;
    private Inventory inventory;
    private Player player;
    private InventoryContents contents;

    @Override
    public InventoryBuilder setup(String t, int s) {
        title = t;
        size = s;

        this.contents = new InventoryContentsImpl(size, this);
        return this;
    }

    @Override
    public InventoryBuilder setup(String t, int s, int page) {
        this.title = t;
        this.size = s;
        this.maxPage = page;

        this.contents = new InventoryContentsImpl(size, this, maxPage);
        return this;
    }

    @Override
    public InventoryBuilder provider(InventoryProvider p) {
        provider = p;
        return this;
    }

    @Override
    public InventoryBuilder build() {
        if (contents == null) throw new IllegalStateException("setup wasn't correctly");

        InventoryFramework.initListener(this);
        inventory = Bukkit.createInventory(null, size, title);

        return this;
    }

    @Override
    public InventoryContents getContents() {
        if (contents == null)throw new IllegalStateException("setup wasn't correctly");
        return contents;
    }


    @Override
    public void open(Player player) {
        this.player = player;

        player.openInventory(inventory);
        provider.init(player, contents);

        for (int i = 0; i < inventory.getSize(); i++) {
            SmartItem item = contents.getItem(i);
            if (item == null) continue;
            inventory.setItem(i, item.getItem());
        }

    }

    @Override
    public void open(Player... players) {
        for (Player player : players) {
            clone().open(player);
        }
    }

    @Override
    public InventoryProvider getProvider() {
        return provider;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public InventoryBuilder clone() {
        if (maxPage == -1) return InventoryFramework.builder()
                .setup(title, size)
                .provider(provider)
                .build();

        return InventoryFramework.builder()
                .setup(title, size, maxPage)
                .provider(provider)
                .build();
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (inventory == null) return;
        if (!e.getInventory().equals(inventory)) return;
        e.setCancelled(true);


        int i = 0;
        for (SmartItem item : contents.getItems()) {
            if (item == null){
                i++;
                continue;
            }
            if (e.getRawSlot() == i) {
                item.getConsumer().accept(e, item);
            }

            i++;
        }

        for (i = 0; i < inventory.getSize(); i++) {
            SmartItem item = contents.getItem(i);
            if (item == null) continue;
            inventory.setItem(i, item.getItem());
        }

    }
}
