package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 08.11.2020
 * Time: 14:39
 * For Project: Pets
 */

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        new PlayerPetManager(event.getPlayer());
    }

}
