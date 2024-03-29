package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.InventoryProvider;
import de.derioo.inventoryframework.objects.InventoryFramework;
import de.derioo.inventoryframework.objects.SmartItem;
import de.derioo.inventoryframework.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.index.qual.NonNegative;

/**
 * An implementation of the InventoryBuilder interface for building inventories and handling inventory interactions.
 */
public class InventoryBuilderImpl implements InventoryBuilder, Listener {

    private Component title = Component.text("NOT_CONFIGURED");
    private int size = 9;
    private int maxPage = -1;
    private InventoryProvider provider;
    private Inventory inventory;
    private Player player;
    private InventoryContents contents;
    private boolean hasBeenInitialized = false;
    private boolean disableCancel = false;
    private boolean disableDoubleClicks;
    private boolean opened = false;

    @Override
    public InventoryBuilder setup(String t, int s) {
        this.title = Component.text(t);
        this.size = s;

        this.contents = new InventoryContentsImpl(this.size, this);
        return this;
    }

    @Override
    public InventoryBuilder setup(Component title, @NonNegative int size) {
        this.title = title;
        this.size = size;

        this.contents = new InventoryContentsImpl(this.size, this);
        return this;
    }

    @Override
    public InventoryBuilder setup(String t, int s, int page) {
        this.title = Component.text(t);
        this.size = s;
        this.maxPage = page;

        this.contents = new InventoryContentsImpl(this.size, this, this.maxPage);
        return this;
    }

    @Override
    public InventoryBuilder setup(Component title, @NonNegative int size, @NonNegative int maxPages) {
        this.title = title;
        this.size = size;
        this.maxPage = maxPages;

        this.contents = new InventoryContentsImpl(this.size, this, this.maxPage);
        return this;
    }

    @Override
    public InventoryBuilder provider(InventoryProvider p) {
        this.provider = p;
        return this;
    }

    @Override
    public InventoryBuilder disableCancelEvents() {
        this.disableCancel = true;
        return this;
    }

    @Override
    public InventoryBuilder disableDoubleClicks() {
        this.disableDoubleClicks = true;
        return this;
    }

    @Override
    public InventoryBuilder build() {
        if (this.contents == null) throw new IllegalStateException("setup wasn't correctly");

        this.contents.setItems(new SmartItem[this.size]);

        if (!this.hasBeenInitialized) InventoryFramework.initListener(this);
        this.inventory = Bukkit.createInventory(null, this.size, this.title);

        this.hasBeenInitialized = true;
        return this;
    }

    @Override
    public InventoryContents getContents() {
        if (this.contents == null) throw new IllegalStateException("setup wasn't correctly");
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

        this.opened = true;

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
        for (int i = 0; i < this.inventory.getSize(); i++) {
            SmartItem item = this.contents.getItem(i);
            if (item == null) {
                this.inventory.setItem(i, new ItemBuilder(Material.AIR).toItemStack());
                continue;
            }
            this.inventory.setItem(i, item.getItem());
        }
    }

    @Override
    public void reload() {
        this.build().open(this.player);
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
     *
     * @param e the click event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (this.inventory == null) return;
        if (!e.getInventory().equals(this.inventory)) return;
        if (!this.disableCancel) e.setCancelled(true);
        if (this.disableDoubleClicks && e.getClick().equals(ClickType.DOUBLE_CLICK)) e.setCancelled(true);

        this.provider.onClick(this.player, this.contents, e);

        int i = 0;
        for (SmartItem item : this.contents.getItems()) {
            if (item == null) {
                i++;
                continue;
            }
            if (e.getRawSlot() == i) {
                item.getConsumer().accept(e, item);
            }

            i++;
        }

        if (!this.disableCancel) this.update();

    }

    /**
     * Used to listen to the event
     *
     * @param e the close event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!this.opened) return;
        if (this.inventory == null) return;
        if (!e.getView().getTopInventory().equals(this.inventory)) return;

        if (!(e.getPlayer() instanceof Player)) return;

        this.provider.onClose((Player) e.getPlayer(), this.contents, e);

    }
}
