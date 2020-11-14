package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 13.11.2020
 * Time: 23:45
 * Project: Pets
 */

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if(Pets.getInstance().getPetsConfig().getDisabledWorlds().contains(player.getLocation().getWorld().getName())) {
            PlayerPetManager playerPetManager = PlayerPetManager.getPlayers().get(player);
            if(playerPetManager.getSpawnedPet() != null) {
                player.sendMessage(Pets.getInstance().getPrefix() + "§7Pets are §cdisabled §7in this world.");
                playerPetManager.disableSpawnedPet();
                playerPetManager.despawnPet();
                playerPetManager.getPetGUI().buildInventories();
            }
        }
    }
}
