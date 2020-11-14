package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 13.11.2020
 * Time: 23:34
 * Project: Pets
 */

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(Pets.getInstance().getPetsConfig().isPetInvulnerability()) return;
        PlayerPetManager.getPlayers().forEach((player, playerPetManager) -> {
            if(playerPetManager.getSpawnedPet() != null) {
                if(playerPetManager.getSpawnedPet().getEntity().equals(event.getEntity())) {
                    PlayerPetManager.getPlayers().get(player).disableSpawnedPet();
                    PlayerPetManager.getPlayers().get(player).despawnPet();
                    PlayerPetManager.getPlayers().get(player).getPetGUI().buildInventories();

                    player.sendMessage(Pets.getInstance().getPrefix() + "§7Your pet has been §ckilled§7.");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_DEATH,1,1);
                    event.getDrops().clear();
                    event.setDroppedExp(0);
                }
            }
        });
    }
}
