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

/**
 * An implementation of the InventoryBuilder interface for building inventories and handling inventory interactions.
 */
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
        this.title = t;
        this.size = s;

        this.contents = new InventoryContentsImpl(this.size, this);
        return this;
    }

    @Override
    public InventoryBuilder setup(String t, int s, int page) {
        this.title = t;
        this.size = s;
        this.maxPage = page;

        this.contents = new InventoryContentsImpl(this.size, this, this.maxPage);
        return this;
    }

    @Override
    public InventoryBuilder provider(InventoryProvider p) {
        this.provider = p;
        return this;
    }

    @Override
    public InventoryBuilder build() {
        if (this.contents == null) throw new IllegalStateException("setup wasn't correctly");

        InventoryFramework.initListener(this);
        this.inventory = Bukkit.createInventory(null, this.size, this.title);

        return this;
    }

    @Override
    public InventoryContents getContents() {
        if (this.contents == null)throw new IllegalStateException("setup wasn't correctly");
        return this.contents;
    }


    @Override
    public void open(Player player) {
        this.player = player;

        player.openInventory(this.inventory);
        this.provider.init(player, this.contents);

        for (int i = 0; i < this.inventory.getSize(); i++) {
            SmartItem item = this.contents.getItem(i);
            if (item == null) continue;
            this.inventory.setItem(i, item.getItem());
        }

    }

    @Override
    public void open(Player... players) {
        for (Player player : players) {
            this.clone().open(player);
        }
    }

    @Override
    public InventoryProvider getProvider() {
        return this.provider;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void update() {
        this.contents.setItems(new SmartItem[28]);
        this.provider.init(player, this.contents);
        for (int i = 0; i < this.inventory.getSize(); i++) {
            SmartItem item = this.contents.getItem(i);
            if (item == null) continue;
            this.inventory.setItem(i, item.getItem());
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public InventoryBuilder clone() {
        if (this.maxPage == -1) return InventoryFramework.builder()
                .setup(this.title, this.size)
                .provider(this.provider)
                .build();

        return InventoryFramework.builder()
                .setup(title, size, maxPage)
                .provider(provider)
                .build();
    }


    /**
     * Used to listen to the event
     * @param e the click event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (this.inventory == null) return;
        if (!e.getInventory().equals(this.inventory)) return;
        e.setCancelled(true);


        int i = 0;
        for (SmartItem item : this.contents.getItems()) {
            if (item == null){
                i++;
                continue;
            }
            if (e.getRawSlot() == i) {
                item.getConsumer().accept(e, item);
            }

            i++;
        }

        update();

    }
}
