package com.voxcrafterlp.pets.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 09:54
 * For Project: Pets
 */

public class ItemManager {

    private ItemStack item;
    private ItemMeta meta;

    public ItemManager(final ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemManager(final Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemManager(final Material material, int id) {
        this.item = new ItemStack(material, 1, (byte) id);
        this.meta = item.getItemMeta();
    }

    public ItemManager setAmount(final int value) {
        item.setAmount(value);
        return this;
    }

    public ItemManager setDisplayName(final String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemManager addDisplayName(final String displayName) {
        meta.setDisplayName(meta.getDisplayName() + displayName);
        return this;
    }

    public ItemManager setNoName() {
        meta.setDisplayName(" ");
        return this;
    }

    public ItemManager addLore(final String... strings) {
        meta.setLore(Arrays.asList(strings));
        return this;
    }

    public ItemManager addLore(final List<String> stringList) {
        meta.setLore(stringList);
        return this;
    }

    public ItemManager setUnbreakable(final boolean unbreakable) {
        meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemManager addItemFlag(final ItemFlag itemFlag) {
        meta.addItemFlags(itemFlag);
        return this;
    }

    public ItemManager addEnchantment(final Enchantment enchantment, final int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemManager setSubID(int id) {
        item.setTypeId(id);
        return this;
    }

    public ItemStack setHeadOwnerAndBuild(String owner) {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwner(owner);
        item.setItemMeta(skullMeta);
        return item;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
