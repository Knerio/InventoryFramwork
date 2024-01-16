package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.PageSystem;
import de.derioo.inventoryframework.objects.SmartItem;
import de.derioo.inventoryframework.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An implementation of the InventoryContents interface
 */
public class InventoryContentsImpl implements InventoryContents {


    private SmartItem[] contents;
    private PageSystem pageSystem;
    private final InventoryBuilder builder;

    private final int size;

    /**
     * Initialises the implementation
     *
     * @param size     the size of the contents
     * @param builder  the builder
     * @param maxPages the max pages
     */
    public InventoryContentsImpl(int size, InventoryBuilder builder, int maxPages) {
        this.contents = new SmartItem[size];
        this.builder = builder;
        this.pageSystem = new PageSystemImpl(maxPages, this);
        this.size = size;
    }

    /**
     * Initialises the implementation
     *
     * @param size    the size of the contents
     * @param builder the builder
     */
    public InventoryContentsImpl(int size, InventoryBuilder builder) {
        this.contents = new SmartItem[size];
        this.builder = builder;
        this.size = size;
    }


    @Override
    public SmartItem[] getItems() {
        return this.contents;
    }

    @Override
    public ItemStack[] getRawItems() {
        ItemStack[] array = new ItemStack[size];
        for (int i = 0; i < this.contents.length; i++) {
            if (this.contents[i] == null) {
                array[i] = new ItemBuilder(Material.AIR).toItemStack();
                continue;
            }
            array[i] = this.contents[i].getItem();
        }
        return array;
    }

    @Override
    public void setItems(SmartItem[] items) {
        this.contents = items;
    }

    @Nullable
    @Override
    public SmartItem getItem(int index) {
        return this.contents[index];
    }

    @Override
    public void set(int index, SmartItem item) {
        this.contents[index] = item;
    }

    @Override
    public void set(int index, ItemStack item) {
        this.set(index, SmartItem.get(item));
    }


    @Override
    public void fill(Material material) {
        this.fill(material, Component.empty());
    }

    @Override
    public void fill(Material material, String name) {
        this.fill(SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
    }

    @Override
    public void fill(Material material, Component name) {
        this.fill(SmartItem.get(new ItemBuilder(material).name(name).toItemStack()));
    }

    @Override
    public void fillBorders(Material material) {
        this.fillBorders(material, " ");
    }

    @Override
    public void fillBorders(Material material, String name) {
        this.fillBorders(SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
    }

    @Override
    public void fillBorders(Material material, Component name) {
        this.fillBorders(SmartItem.get(new ItemBuilder(material).name(name).toItemStack()));
    }

    @Override
    public void fillBorders(SmartItem item) {
        for (int i = 0; i < 10; i++) {
            this.set(i, item);
        }
        int size = this.getItems().length;
        if (size > 9) {
            for (int i = size - 9; i < size; i++) {
                this.set(i, item);
            }
        }
        int rows = (size + 1) / 9;

        for (int i = 9; i < rows * 9 - 1; i += 9) {
            if (i == 0) continue;
            this.set(i, item);
            this.set(i + 8, item);
        }
    }

    @Override
    public void fill(SmartItem item) {
        for (int i = 0; i < this.contents.length; i++) {
            this.contents[i] = item.clone();
        }
    }

    @Override
    public PageSystem getPageSystem() {
        if (this.pageSystem == null)
            throw new IllegalStateException("PageSystem is null because this.setup want correctly");
        return this.pageSystem;
    }

    @Override
    public InventoryBuilder getBuilder() {
        return this.builder;
    }

    @Override
    public void update() {
        if (this.pageSystem == null) this.pageSystem = new PageSystemImpl(1, this.builder.getContents());
        this.getBuilder().update();
    }

    @Override
    public Inventory changeTitle(String newTitle) {
        return this.changeTitle(Component.text(newTitle));
    }

    @Override
    public Inventory changeTitle(Component newTitle) {

        int size = this.builder.getInventory().getSize();

        Inventory newInventory = Bukkit.createInventory(this.builder.getInventory().getHolder(), size, newTitle);

        newInventory.setContents(this.builder.getInventory().getContents());


        for (HumanEntity viewer : new ArrayList<>(this.builder.getInventory().getViewers())) {
            viewer.openInventory(newInventory);
        }


        builder.setInventory(newInventory);

        return newInventory;
    }


}
