package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 10.11.2020
 * Time: 17:11
 * For Project: Pets
 */

public class EntityDamageListener implements Listener {

    /*
     * Prevents pets fom taking damage
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!Pets.getInstance().getPetsConfig().isPetInvulnerability()) return;
        PlayerPetManager.getPlayers().forEach((player, playerPetManager) -> {
            if(playerPetManager.getSpawnedPet() != null) {
                if(playerPetManager.getSpawnedPet().getEntity().equals(event.getEntity()))
                    event.setCancelled(true);
            }
        });
    }

}
