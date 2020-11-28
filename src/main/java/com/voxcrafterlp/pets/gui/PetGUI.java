package com.voxcrafterlp.pets.gui;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import com.voxcrafterlp.pets.utils.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 09:52
 * For Project: Pets
 */

@Getter
public class PetGUI {

    private Inventory welcomeInventory;
    private Inventory shopInventory;
    private Inventory ownInventory;

    private final Player player;

    public PetGUI(Player player) {
        this.player = player;
        this.buildInventories();
    }

    public void buildInventories() {
        //==================================================//
        {
            this.welcomeInventory = Bukkit.createInventory(null, 27, Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-title") + " v" + Pets.getInstance().getDescription().getVersion());
            for(int i = 0; i<27; i++)
                this.welcomeInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.welcomeInventory.setItem(11, new ItemManager(Material.ENDER_CHEST).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-shop")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-shop-description"))).build());
            this.welcomeInventory.setItem(15, new ItemManager(Material.CHEST).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-pets")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-pets-description"))).build());
        }
        //==================================================//
        {
            this.ownInventory = Bukkit.createInventory(null, 54, Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-title"));
            for(int i = 36; i<45; i++)
                this.ownInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.ownInventory.setItem(53, new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-button-back")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-button-back-description"))).setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"));

            if(PlayerPetManager.getPlayers().get(player).getPets().isEmpty())
                this.ownInventory.setItem(13, new ItemManager(Material.BARRIER).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-nopets")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-nopets-description"))).build());
            else {
                AtomicInteger slot = new AtomicInteger(0);

                PlayerPetManager.getPlayers().get(player).getPets().forEach(petData -> {
                    for(PetType petType : PetType.values()) {
                        if(petData.getPetType().equalsIgnoreCase(petType.getClassName())) {
                            if(Pets.getInstance().getPetsConfig().getEnabledPets().get(petType.getClassName())) {
                                if(petData.isEnabled())
                                    this.ownInventory.setItem(slot.get(), new ItemManager(petType.getIcon().clone()).addDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-active")).build());
                                else
                                    this.ownInventory.setItem(slot.get(), new ItemManager(petType.getIcon().clone()).addDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-inactive")).build());
                                slot.getAndIncrement();
                            }
                        }
                    }
                });
            }
        }
        //==================================================//
        {
            this.shopInventory = Bukkit.createInventory(null, 54, Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-title"));
            for(int i = 36; i<45; i++)
                this.shopInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.shopInventory.setItem(53, new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-button-back")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-button-back-description"))).setHeadValueAndBuild("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"));

            int slot = 0;

            for(PetType petType : PetType.values()) {
                if(Pets.getInstance().getPetsConfig().getEnabledPets().get(petType.getClassName())) {
                    int finalSlot = slot;
                    if(PlayerPetManager.getPlayers().get(player).getPets().isEmpty())
                        shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-price") + Pets.getInstance().getPetsConfig().getPetPrices().get(petType.getClassName())).build());
                     else {
                         AtomicBoolean contains = new AtomicBoolean(false);

                        PlayerPetManager.getPlayers().get(player).getPets().forEach(petData -> {
                            if(petData.getPetType().equalsIgnoreCase(petType.getClassName()))
                                contains.set(true);
                        });

                        if(contains.get())
                            shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-purchased")).build());
                        else
                            shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-price") + Pets.getInstance().getPetsConfig().getPetPrices().get(petType.getClassName())).build());
                    }
                    slot++;
                }
            }
        }
    }
}
