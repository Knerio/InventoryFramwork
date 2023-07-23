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
public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount){
        this.itemStack = new ItemStack(material, amount);
    }
    public ItemBuilder(ItemStack item){
        this.itemStack = item;
    }

    /**
     *
     * @param name sets the specified name
     */
    public ItemBuilder setName(String name){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     * @param material sets the specified material
     */
    public ItemBuilder setType(Material material){
        itemStack.setType(material);
        return this;
    }

    /**
     *  Hides all item flags
     * @see ItemBuilder#addFlags(ItemFlag...)
     */
    public ItemBuilder hideAllFlags(){
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
     *
     *
     *  @param flags adds specified ItemFlags
     */
    public ItemBuilder addFlags(ItemFlag... flags){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flags);
        return this;
    }

    /**
     *
     *
     *  @param flags removes specified ItemFlags
     */
    public ItemBuilder removeFlags(ItemFlag... flags){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeItemFlags(flags);
        return this;
    }

    /**
     *
     *
     *  @param enchantment adds the specified enchantment
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean restriction){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, restriction);
        return this;
    }

    /**
     *
     *
     *  @param enchantment removes the specified enchantment
     */
    public ItemBuilder removeEnchant(Enchantment enchantment){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeEnchant(enchantment);
        return this;
    }


    /**
     *
     *
     *  @param lore adds the specified lore's
     */
    public ItemBuilder addLore(String... lore){
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> oldLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        oldLore.addAll(Arrays.asList(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     *
     *  @param lore set the specified lore's
     */
    public ItemBuilder setLore(String... lore){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     *
     *  @param lore sets the lore
     */
    public ItemBuilder setLore(List<String> lore){
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     *
     *  @param lore removes the specified lore's
     */
    public ItemBuilder removeLore(String... lore){
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> oldLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        oldLore.removeAll(Arrays.asList(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     *
     *  @param amount the new item amount
     */
    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     *
     *
     *  @param color the new color of the armor
     *  @throws IllegalArgumentException if the material is not an leatherAmor
     *
     */
    public ItemBuilder setLeatherColor(Color color) throws IllegalArgumentException{
        if (!this.itemStack.getType().toString().toLowerCase().contains("leather_"))throw new IllegalArgumentException("Head needs LeatherArmor as Material");
        LeatherArmorMeta meta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        meta.setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     *
     *
     *  @param url the skull texture url
     *  @throws IllegalArgumentException if the material is not an skull
     *
     */
    public ItemBuilder setCustomSkull(String url)  throws IllegalArgumentException{
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)){
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.itemStack.setItemMeta((ItemMeta)headMeta);
        return this;
    }

    /**
     *
     *
     *  @param url the skull texture url
     *  @throws IllegalArgumentException if the material is not an skull
     *
     */
    public ItemBuilder setCustomSkullWithValue(String data)  throws IllegalArgumentException{
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)){
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
        this.itemStack.setItemMeta((ItemMeta)headMeta);
        return this;
    }

    /**
     *
     *
     *  @param player the player Of the Skull
     *  @throws IllegalArgumentException if the material is not an skull
     *
     */
    public ItemBuilder setSkull(Player player)  throws IllegalArgumentException{
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)){
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();
        headMeta.setOwningPlayer(player);
        this.itemStack.setItemMeta((ItemMeta)headMeta);
        return this;
    }

    /**
     *
     *
     *  @param player the player Of the Skull
     *  @throws IllegalArgumentException if the material is not an skull
     *   @see ItemBuilder#setSkull(Player)
     *
     */
    public ItemBuilder setSkull(String player)  throws IllegalArgumentException{
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)){
            throw new IllegalArgumentException("Head needs PlayerHead as Material");
        }

        SkullMeta headMeta = (SkullMeta) this.itemStack.getItemMeta();
        headMeta.setOwner(player);
        this.itemStack.setItemMeta((ItemMeta)headMeta);
        return this;
    }

    /**
     *
     *  @return ItemStack the final ItemStack
     *
     */
    public ItemStack toItemStack(){
        return this.itemStack;
    }

    /**
     *
     *  @return ItemStack the final ItemStack
     *
     */
    public ItemStack build(){
        return this.itemStack;
    }

}

