package de.derioo.inventoryframework.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A utility class to build custom ItemStacks with various attributes and properties.
 */
public class ItemBuilder {

    private final ItemStack itemStack;

    /**
     * Creates a new ItemBuilder with the specified material.
     *
     * @param material The material of the ItemStack.
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    /**
     * Creates a new ItemBuilder with the specified material and amount.
     *
     * @param material The material of the ItemStack.
     * @param amount   The amount of items in the stack.
     */
    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    /**
     * Creates a new ItemBuilder from an existing ItemStack.
     *
     * @param item The ItemStack to build upon.
     */
    public ItemBuilder(ItemStack item) {
        this.itemStack = item;
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The name to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the material of the item.
     *
     * @param material The material to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setType(Material material) {
        itemStack.setType(material);
        return this;
    }

    /**
     * Hides all item flags.
     * @see ItemBuilder#addFlags(ItemFlag...)
     * @return The ItemBuilder instance.
     */
    public ItemBuilder hideAllFlags() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_UNBREAKABLE,
                ItemFlag.HIDE_POTION_EFFECTS);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Adds the specified ItemFlags to the item.
     *
     * @param flags The flags to add.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes the specified ItemFlags from the item.
     *
     * @param flags The flags to remove.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder removeFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeItemFlags(flags);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level of the enchantment.
     * @param restriction Whether to ignore level restrictions.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean restriction) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, restriction);
        return this;
    }

    /**
     * Removes the specified enchantment from the item.
     *
     * @param enchantment The enchantment to remove.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeEnchant(enchantment);
        return this;
    }

    /**
     * Adds lore lines to the item.
     *
     * @param lore The lore lines to add.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder addLore(String... lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> oldLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        oldLore.addAll(Arrays.asList(lore));
        meta.setLore(oldLore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the lore lines of the item.
     *
     * @param lore The lore lines to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the lore lines of the item.
     *
     * @param lore The list of lore lines to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Removes lore lines from the item.
     *
     * @param lore The lore lines to remove.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder removeLore(String... lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> oldLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        oldLore.removeAll(Arrays.asList(lore));
        meta.setLore(oldLore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the amount of items in the stack.
     *
     * @param amount The new item amount.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the leather color of the armor item.
     *
     * @param color The new color of the armor.
     * @return The ItemBuilder instance.
     * @throws IllegalArgumentException if the material is not leather armor.
     */
    public ItemBuilder setLeatherColor(Color color) throws IllegalArgumentException {
        if (!this.itemStack.getType().toString().toLowerCase().contains("leather_"))
            throw new IllegalArgumentException("Head needs LeatherArmor as Material");
        LeatherArmorMeta meta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        meta.setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets a custom skull texture for the player head.
     *
     * @param url The skull texture URL.
     * @return The ItemBuilder instance.
     * @throws IllegalArgumentException if the material is not a player head.
     */
    public ItemBuilder setCustomSkull(String url) throws IllegalArgumentException {
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)) {
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[]{url}).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.itemStack.setItemMeta((ItemMeta) headMeta);
        return this;
    }

    /**
     * Sets a custom skull texture for the player head using the raw texture data.
     *
     * @param data The skull texture data.
     * @return The ItemBuilder instance.
     * @throws IllegalArgumentException if the material is not a player head.
     */
    public ItemBuilder setCustomSkullWithValue(String data) throws IllegalArgumentException {
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)) {
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.itemStack.setItemMeta((ItemMeta) headMeta);
        return this;
    }

    /**
     * Sets the skull owner to the specified player for the player head item.
     *
     * @param player The player owning the skull.
     * @return The ItemBuilder instance.
     * @throws IllegalArgumentException if the material is not a player head.
     */
    public ItemBuilder setSkull(Player player) throws IllegalArgumentException {
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)) {
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();
        headMeta.setOwningPlayer(player);
        this.itemStack.setItemMeta((ItemMeta) headMeta);
        return this;
    }

    /**
     * Sets the skull owner to the specified player name for the player head item.
     *
     * @param player The name of the player owning the skull.
     * @return The ItemBuilder instance.
     * @throws IllegalArgumentException if the material is not a player head.
     * @see ItemBuilder#setSkull(Player)
     */
    public ItemBuilder setSkull(String player) throws IllegalArgumentException {
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)) {
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();
        headMeta.setOwner(player);
        this.itemStack.setItemMeta((ItemMeta) headMeta);
        return this;
    }

    /**
     * Builds the final ItemStack.
     *
     * @return The built ItemStack.
     */
    public ItemStack toItemStack() {
        return this.itemStack;
    }

    /**
     * Builds the final ItemStack.
     *
     * @return The built ItemStack.
     */
    public ItemStack build() {
        return this.itemStack;
    }
}