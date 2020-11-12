package com.voxcrafterlp.pets.gui;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
                PlayerPetManager.getPlayers().get(player).despawnPet();
                player.sendMessage(Pets.getInstance().getPrefix() + "§7You picked your §cpet §7up.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
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
}
