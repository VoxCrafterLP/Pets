package com.voxcrafterlp.pets.gui;

import com.google.common.collect.Lists;
import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import com.voxcrafterlp.pets.objects.PetData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 08.11.2020
 * Time: 14:43
 * For Project: Pets
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getInventory().getName() == null) return;
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        Player player = (Player) event.getWhoClicked();

        if(event.getInventory().getName().equals(" §8➜ §cPets v" + Pets.getInstance().getDescription().getVersion())) {
            event.setCancelled(true);
            if(event.getCurrentItem().getType() == Material.CHEST) {
                player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getOwnInventory());
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
            if(event.getCurrentItem().getType() == Material.ENDER_CHEST) {
                player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getShopInventory());
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
        }
        if(event.getInventory().getName().equals(" §8➜ §cYour pets")) {
            event.setCancelled(true);
            if(event.getSlot() == 53) {
                player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getWelcomeInventory());
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
            if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                PetType petType = PetType.getPetTypeFromDisplayName(event.getCurrentItem().getItemMeta().getDisplayName());
                PlayerPetManager playerPetManager = PlayerPetManager.getPlayers().get(player);

                if(playerPetManager.getSpawnedPet() != null) {
                    if(playerPetManager.getSpawnedPet().getPetType().equals(petType)) {
                        player.sendMessage(Pets.getInstance().getPrefix() + "§7You despawned your §cpet§7.");
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                        playerPetManager.disableSpawnedPet();
                        playerPetManager.despawnPet();
                        try {
                            playerPetManager.getPetGUI().buildInventories();
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            player.sendMessage(Pets.getInstance().getPrefix() + "§cSomething went wrong! Please read the logs for more information!");
                            e.printStackTrace();
                        }
                        return;
                    }
                }

                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

                try {
                    playerPetManager.spawnPet(new PetData(player, petType.getClassName(), getRandomColorCode() + petType.getClassName(), player.getLocation(), false, true));
                    player.sendMessage(Pets.getInstance().getPrefix() + "§b" + petType.getClassName() + " §7summoned successfully. §8(§7Rightclick to manage§8)");
                    playerPetManager.getPetGUI().buildInventories();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    player.sendMessage(Pets.getInstance().getPrefix() + "§cSomething went wrong! Please read the logs for more information!");
                    e.printStackTrace();
                }
            }
        }
        if(event.getInventory().getName().equals(" §8➜ §bShop")) {
            event.setCancelled(true);
            if(event.getSlot() == 53) {
                player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getWelcomeInventory());
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
        }
        if(event.getInventory().getName().equals(" §8➜ §cPet")) {
            event.setCancelled(true);
            if(event.getCurrentItem().getType() == Material.BARRIER) {
                PlayerPetManager.getPlayers().get(player).disableSpawnedPet();
                PlayerPetManager.getPlayers().get(player).despawnPet();
                player.sendMessage(Pets.getInstance().getPrefix() + "§7You picked your §cpet §7up.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                try {
                    PlayerPetManager.getPlayers().get(player).getPetGUI().buildInventories();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    player.sendMessage(Pets.getInstance().getPrefix() + "§cSomething went wrong! Please read the logs for more information!");
                    e.printStackTrace();
                }
                return;
            }
            if(event.getCurrentItem().getType() == Material.HAY_BLOCK) {
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().setSitting(true);
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().updateSitting();
                player.sendMessage(Pets.getInstance().getPrefix() + "§7Your pet is now §bsitting§7.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
            if(event.getCurrentItem().getType() == Material.NOTE_BLOCK) {
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().setSitting(false);
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().updateSitting();
                player.sendMessage(Pets.getInstance().getPrefix() + "§7Your pet is now §bfollowing §7you.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
        }
    }

    private String getRandomColorCode() {
        String[] string = new String[]{"§a", "§2", "§c", "§d", "§5", "§b", "§6"};
        return string[new Random().nextInt(string.length)];
    }

}
