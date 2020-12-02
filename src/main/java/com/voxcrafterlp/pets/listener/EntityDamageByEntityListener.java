package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 02.12.2020
 * Time: 11:45
 * Project: Pets
 */

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            PlayerPetManager playerPetManager = PlayerPetManager.getPlayers().get(player);

            if(playerPetManager.getSpawnedPet() == null)
                return;
            if(playerPetManager.getSpawnedPet().getEntity().equals(event.getDamager()))
                event.setCancelled(true);
        }
    }

}
