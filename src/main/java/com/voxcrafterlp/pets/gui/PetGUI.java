package com.voxcrafterlp.pets.gui;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.custompets.CustomPet;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import com.voxcrafterlp.pets.utils.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
            this.welcomeInventory = Bukkit.createInventory(null, 27, " §8➜ §cPets v" + Pets.getInstance().getDescription().getVersion());
            for(int i = 0; i<27; i++)
                this.welcomeInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.welcomeInventory.setItem(11, new ItemManager(Material.ENDER_CHEST).setDisplayName(" §7➥ §bShop").addLore("§8§m------------------", " ", "§7Here you can buy new pets", " ", "§8§m------------------").build());
            this.welcomeInventory.setItem(15, new ItemManager(Material.CHEST).setDisplayName(" §7➥ §cYour pets").addLore("§8§m------------------", " ", "§7Here you can manage your", "§7own pets", " ", "§8§m------------------").build());
        }
        //==================================================//
        {
            this.ownInventory = Bukkit.createInventory(null, 54, " §8➜ §cYour pets");
            for(int i = 36; i<45; i++)
                this.ownInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.ownInventory.setItem(53, new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §cBack").addLore("§8§m------------------", " ", "§7Go back to the menu", " ", "§8§m------------------").setHeadOwnerAndBuild("MHF_ArrowRight"));

            if(PlayerPetManager.getPlayers().get(player).getPets().isEmpty())
                this.ownInventory.setItem(13, new ItemManager(Material.BARRIER).setDisplayName("§cNo pets").addLore("§8§m------------------", " ", "§7You don't have any pets.", "§7You can buy them in the §bshop§7.", " ", "§8§m------------------").build());
            else {
                AtomicInteger slot = new AtomicInteger(0);

                PlayerPetManager.getPlayers().get(player).getPets().forEach(petData -> {
                    for(PetType petType : PetType.values()) {
                        if(petData.getPetType().equalsIgnoreCase(petType.getClassName())) {
                            if(Pets.getInstance().getPetsConfig().getEnabledPets().get(petType.getClassName())) {
                                if(petData.isEnabled())
                                    this.ownInventory.setItem(slot.get(), new ItemManager(petType.getIcon().clone()).addDisplayName(" §7| §aactive").build());
                                else
                                    this.ownInventory.setItem(slot.get(), new ItemManager(petType.getIcon().clone()).addDisplayName(" §7| §cinactive").build());
                                slot.getAndIncrement();
                            }
                        }
                    }
                });
            }
        }
        //==================================================//
        {
            this.shopInventory = Bukkit.createInventory(null, 54, " §8➜ §bShop");
            for(int i = 36; i<45; i++)
                this.shopInventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());
            this.shopInventory.setItem(53, new ItemManager(Material.SKULL_ITEM, 3).setDisplayName(" §7➥ §cBack").addLore("§8§m------------------", " ", "§7Go back to the menu", " ", "§8§m------------------").setHeadOwnerAndBuild("MHF_ArrowRight"));

            int slot = 0;

            for(PetType petType : PetType.values()) {
                if(Pets.getInstance().getPetsConfig().getEnabledPets().get(petType.getClassName())) {
                    int finalSlot = slot;
                    if(PlayerPetManager.getPlayers().get(player).getPets().isEmpty()) {
                        shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(" §7| Price§8: §b" + Pets.getInstance().getPetsConfig().getPetPrices().get(petType.getClassName())).build());
                    } else
                        PlayerPetManager.getPlayers().get(player).getPets().forEach(petData -> {
                            if(petData.getPetType().equalsIgnoreCase(petType.getClassName()))
                                shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(" §7| §apurchased").build());
                            else
                                shopInventory.setItem(finalSlot, new ItemManager(petType.getIcon().clone()).addDisplayName(" §7| Price§8: §b" + Pets.getInstance().getPetsConfig().getPetPrices().get(petType.getClassName())).build());
                        });
                    slot++;
                }
            }
        }

    }

}
