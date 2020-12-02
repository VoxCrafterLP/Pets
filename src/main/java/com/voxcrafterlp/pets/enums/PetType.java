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

    CHICKEN("ChickenPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bChickenPet").addLore("§8§m------------------", " ", "§7Saturation", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==")),
    CAT("CatPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bCatPet").addLore("§8§m------------------", " ", "§7Speed I (10x10)", "§7Haste I", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyNTNmYzZiNjU2OTg4NDUzYTJkNzEzOGZjYTRkMWYyNzUyZjQ3NjkxZjBjNDM0ZTQzMjE4Mzc3MWNmZTEifX19")),
    WOLF("WolfPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bWolfPet").addLore("§8§m------------------", " ", "§7Strength I", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzljYjdkNWM2ZTI5YjNmZWJiZTk2NmQ3YzU4YTNlYzEyMzcyN2YyZDU3ODBkMzg4OTFjNzJkYzlkYzBhOGU0In19fQ==")),
    SLIME("SlimePet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bSlimePet").addLore("§8§m------------------", " ", "§7Fireresistance I", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIwZTg0ZDMyZDFlOWM5MTlkM2ZkYmI1M2YyYjM3YmEyNzRjMTIxYzU3YjI4MTBlNWE0NzJmNDBkYWNmMDA0ZiJ9fX0=")),
    RABBIT("RabbitPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bRabbitPet").addLore("§8§m------------------", " ", "§7Jumpboost I", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0=")),
    MUSHROOM("MushroomCowPet", new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §bMushroomCowPet").addLore("§8§m------------------", " ", "§7Resistance I", " ", "§8§m------------------").setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI1Mjg0MWYyZmQ1ODllMGJjODRjYmFiZjllMWMyN2NiNzBjYWM5OGY4ZDZiM2RkMDY1ZTU1YTRkY2I3MGQ3NyJ9fX0="));


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

    public static  PetType getPetTypeFromDisplayName(String string) {
        for(PetType petType : PetType.values()) {
            if(string.startsWith(petType.getIcon().getItemMeta().getDisplayName()))
                return petType;
        }
        return null;
    }
}
