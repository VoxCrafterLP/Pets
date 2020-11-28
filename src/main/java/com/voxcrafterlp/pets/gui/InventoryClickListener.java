package com.voxcrafterlp.pets.gui;

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

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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

        if(event.getInventory().getName().equals(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-welcome-title") + " v" + Pets.getInstance().getDescription().getVersion())) {
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
        if(event.getInventory().getName().equals(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-title"))) {
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
                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("message-pet-despawned"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                        playerPetManager.disableSpawnedPet();
                        playerPetManager.despawnPet();
                        playerPetManager.getPetGUI().buildInventories();
                        return;
                    }
                    playerPetManager.disableSpawnedPet();
                    playerPetManager.despawnPet();
                    playerPetManager.getPetGUI().buildInventories();
                }

                if(Pets.getInstance().getPetsConfig().getDisabledWorlds().contains(player.getLocation().getWorld().getName())) {
                    player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("message-pets-disabled"));
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                    return;
                }

                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

                try {
                    playerPetManager.spawnPet(new PetData(player, petType.getClassName(), getRandomColorCode() + petType.getClassName(), player.getLocation(), false, true));
                    player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-pets-spawned").replace("{0}", petType.getClassName()));
                    playerPetManager.getPetGUI().buildInventories();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("error-message"));
                    e.printStackTrace();
                }
            }
        }
        if(event.getInventory().getName().equals(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-title"))) {
            event.setCancelled(true);
            if(event.getSlot() == 53) {
                player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getWelcomeInventory());
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
            if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                PetType petType = PetType.getPetTypeFromDisplayName(event.getCurrentItem().getItemMeta().getDisplayName());
                PlayerPetManager playerPetManager = PlayerPetManager.getPlayers().get(player);

                AtomicBoolean bought = new AtomicBoolean(false);

                playerPetManager.getPets().forEach(petData -> {
                    if(petData.getPetType().equalsIgnoreCase(petType.getClassName())) {
                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-already-purchased"));
                        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                        bought.set(true);
                    }
                });

                if(bought.get()) return;

                double balance = Pets.getInstance().getEconomy().getBalance(player);
                double price = Pets.getInstance().getPetsConfig().getPetPrices().get(petType.getClassName());
                double difference = balance - price;

                if(difference < 0) {
                    player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-no-money"));
                    player.playSound(player.getLocation(), Sound.ITEM_BREAK,1,1);
                    return;
                }

                Pets.getInstance().getEconomy().withdrawPlayer(player, price);

                try {
                    playerPetManager.getPets().add(new PetData(player, petType.getClassName(), petType.getClassName(), null, false, false));
                    playerPetManager.savePlayerData();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-shop-success").replace("{0}", petType.getClassName()).replace("{1}", Pets.getInstance().getEconomy().format(price)));
                player.playSound(player.getLocation(), Sound.LEVEL_UP,1,1);

                playerPetManager.getPetGUI().buildInventories();
                player.openInventory(playerPetManager.getPetGUI().getShopInventory());
            }
        }
        if(event.getInventory().getName().equals(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-title"))) {
            event.setCancelled(true);
            if(event.getCurrentItem().getType() == Material.BARRIER) {
                PlayerPetManager.getPlayers().get(player).disableSpawnedPet();
                PlayerPetManager.getPlayers().get(player).despawnPet();
                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-pickedup"));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                PlayerPetManager.getPlayers().get(player).getPetGUI().buildInventories();
                return;
            }
            if(event.getCurrentItem().getType() == Material.HAY_BLOCK) {
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().setSitting(true);
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().updateSitting();
                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-sitting"));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
            if(event.getCurrentItem().getType() == Material.NOTE_BLOCK) {
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().setSitting(false);
                PlayerPetManager.getPlayers().get(player).getSpawnedPet().updateSitting();
                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-following"));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
                return;
            }
        }
    }

    private String getRandomColorCode() {
        if(Pets.getInstance().getPetsConfig().isRandomColor()) {
            String[] string = new String[]{"§a", "§2", "§c", "§d", "§5", "§b", "§6"};
            return string[new Random().nextInt(string.length)];
        } else return "§c";
    }

}
