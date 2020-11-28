package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.manager.PlayerPetManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 09.11.2020
 * Time: 16:30
 * For Project: Pets
 */

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        try {
            PlayerPetManager.getPlayers().get(event.getPlayer()).delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
