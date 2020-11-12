package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.manager.PlayerPetManager;
import com.voxcrafterlp.pets.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 10.11.2020
 * Time: 17:17
 * For Project: Pets
 */

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if(PlayerPetManager.getPlayers().get(player).getSpawnedPet() != null) {
            if(PlayerPetManager.getPlayers().get(player).getSpawnedPet().getEntity().equals(event.getRightClicked())) {
                Inventory inventory = Bukkit.createInventory(null, 27, " §8➜ §cPet");

                for(int i = 0; i<27; i++)
                    inventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());

                if(!PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().isSitting())
                    inventory.setItem(11, new ItemManager(Material.HAY_BLOCK).setDisplayName(" §7➥ §bSit down").addLore("§8§m------------------", " ", "§7Tell your pet to", "§7sit down", " ", "§8§m------------------").build());
                else
                    inventory.setItem(11, new ItemManager(Material.NOTE_BLOCK).setDisplayName(" §7➥ §bFollow").addLore("§8§m------------------", " ", "§7Tell your pet to", "§7follow you", " ", "§8§m------------------").build());

                inventory.setItem(13, PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetType().getIcon().clone());
                inventory.setItem(15, new ItemManager(Material.BARRIER).setDisplayName(" §7➥ §cPick up").addLore("§8§m------------------", " ", "§7Puts your pet in", "§7your bag", " ", "§8§m------------------").build());


                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
            }
        }
    }

}
