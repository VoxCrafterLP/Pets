package com.voxcrafterlp.pets.listener;

import com.voxcrafterlp.pets.Pets;
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
                event.setCancelled(true);
                Inventory inventory = Bukkit.createInventory(null, 27, Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-title"));

                for(int i = 0; i<27; i++)
                    inventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE, 15).setNoName().build());

                if(!PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetData().isSitting())
                    inventory.setItem(11, new ItemManager(Material.HAY_BLOCK).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-sit")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-sit-description"))).build());
                else
                    inventory.setItem(11, new ItemManager(Material.NOTE_BLOCK).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-follow")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-follow-description"))).build());

                inventory.setItem(13, PlayerPetManager.getPlayers().get(player).getSpawnedPet().getPetType().getIcon().clone());
                inventory.setItem(15, new ItemManager(Material.BARRIER).setDisplayName(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-pickup")).addLore(Pets.getInstance().getLanguageLoader().buildDescription(Pets.getInstance().getLanguageLoader().getTranslationByKey("inventory-singlepet-pickup-description"))).build());


                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);
            }
        }
    }

}
