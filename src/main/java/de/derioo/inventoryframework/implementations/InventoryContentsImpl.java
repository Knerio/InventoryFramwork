package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.PageSystem;
import de.derioo.inventoryframework.objects.SmartItem;
import de.derioo.inventoryframework.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

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
     * @param size the size of the contents
     * @param builder the builder
     * @param maxPages the max pages
     */
    public InventoryContentsImpl(int size, InventoryBuilder builder, int maxPages) {
        contents = new SmartItem[size];
        this.builder = builder;
        pageSystem = new PageSystemImpl(maxPages, this);
        this.size = size;
    }

    /**
     * Initialises the implementation
     * @param size the size of the contents
     * @param builder the builder
     */
    public InventoryContentsImpl(int size, InventoryBuilder builder) {
        contents = new SmartItem[size];
        this.builder = builder;
        this.size = size;
    }


    @Override
    public SmartItem[] getItems() {
        return contents;
    }

    @Override
    public ItemStack[] getRawItems() {
        ItemStack[] array = new ItemStack[size];
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null){
                array[i] = new ItemBuilder(Material.AIR).toItemStack();
                continue;
            }
            array[i] = contents[i].getItem();
        }
        return array;
    }

    @Override
    public void setItems(SmartItem[] items) {
        contents = items;
    }

    @Nullable
    @Override
    public SmartItem getItem(int index) {
        return contents[index];
    }

    @Override
    public void set(int index, SmartItem item) {
        contents[index] = item;
    }

    @Override
    public void set(int index, ItemStack item) {
        this.set(index, SmartItem.get(item));
    }


    @Override
    public void fill(Material material) {
        fill(material, " ");
    }

    @Override
    public void fill(Material material, String name) {
        fill(SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
    }

    @Override
    public void fillBorders(Material material) {
        fillBorders(material, " ");
    }

    @Override
    public void fillBorders(Material material, String name) {
        fillBorders(SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
    }

    @Override
    public void fillBorders(SmartItem item) {
        for (int i = 0; i < 10; i++) {
            this.set(i, item);
        }
        int size = getItems().length;
        if (size > 9){
            for (int i = size-9; i < size; i++) {
                this.set(i, item);
            }
        }
        int rows = (size + 1) / 9;

        for(int i = 9; i < rows * 9 - 1; i += 9) {
            if (i == 0)continue;
            this.set(i, item);
            this.set(i+8, item);
        }
    }

    @Override
    public void fill(SmartItem item) {
        for (int i = 0; i < contents.length; i++) {
            contents[i] = item.clone();
        }
    }

    @Override
    public PageSystem getPageSystem() {
        if (pageSystem == null) throw new IllegalStateException("PageSystem is null because this.setup want correctly");
        return pageSystem;
    }

    @Override
    public InventoryBuilder getBuilder() {
        return builder;
    }

    @Override
    public void update() {
        if (pageSystem == null) pageSystem = new PageSystemImpl(1, builder.getContents());
        getBuilder().update();
    }


}
