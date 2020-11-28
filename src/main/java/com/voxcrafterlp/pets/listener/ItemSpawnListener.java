package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 10.11.2020
 * Time: 16:58
 * For Project: Pets
 */

public class ItemSpawnListener implements Listener {

    /**
     * Prevents chicken pets from laying eggs
     */
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();

        if(item.getItemStack().getType() == Material.EGG) {
            item.getNearbyEntities(0.5D, 1.0D, 0.5D).forEach(entity -> {
                if(entity.getType() == EntityType.CHICKEN) {
                    PlayerPetManager.getPlayers().forEach((player, playerPetManager) -> {
                        if(playerPetManager.getSpawnedPet() != null) {
                            if(playerPetManager.getSpawnedPet().getEntity().equals(entity))
                                event.setCancelled(true);
                        }
                    });
                }
            });
        }
    }
}
