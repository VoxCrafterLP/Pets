package com.voxcrafterlp.pets.enums;

import com.voxcrafterlp.pets.utils.ItemManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 08.11.2020
 * Time: 11:35
 * For Project: Pets
 */

@Getter
public enum PetType {

    CHICKEN("ChickenPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bChickenPet").addLore("§8§m------------------", " ", "§7Saturation (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("MHF_Chicken")),
    CAT("CatPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bCatPet").addLore("§8§m------------------", " ", "§7Speed I (10x10)", "§7Haste I (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("jarkpzf")),
    WOLF("WolfPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bWolfPet").addLore("§8§m------------------", " ", "§7Strength I (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("Olinek122")),
    SLIME("SlimePet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bSlimePet").addLore("§8§m------------------", " ", "§7Fireresistance I (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("MHF_Slime")),
    SQUID("SquidPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bSquidPet").addLore("§8§m------------------", " ", "§7Waterbreathing I (10x10)", "§7Nightvision I (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("MHF_Squid")),
    RABBIT("RabbitPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bRabbitPet").addLore("§8§m------------------", " ", "§7Jumpboost I (10x10)", " ", "§8§m------------------").setHeadOwnerAndBuild("albino"));

    private final String className;
    private final ItemStack icon;

    PetType(String className, ItemStack icon) {
        this.className = className;
        this.icon = icon;
    }

    public static PetType getPetTypeFromClassName(String className) {
        for(PetType petType : PetType.values()) {
            if(petType.getClassName().equalsIgnoreCase(className.toLowerCase()))
                return petType;
        }
        return null;
    }

    public static  PetType getPetTypeFromIcon(ItemStack icon) {
        for(PetType petType : PetType.values()) {
            if(petType.getIcon().equals(icon))
                return petType;
        }
        return null;
    }
}
