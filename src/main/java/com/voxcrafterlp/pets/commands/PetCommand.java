package com.voxcrafterlp.pets.commands;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.manager.PlayerPetManager;
import com.voxcrafterlp.pets.objects.PetData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 08.11.2020
 * Time: 11:58
 * For Project: Pets
 */

public class PetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(args.length == 0) {
            player.openInventory(PlayerPetManager.getPlayers().get(player).getPetGUI().getWelcomeInventory());
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 2, 2);
        } else {
            if(!player.hasPermission(Pets.getInstance().getPetsConfig().getAdminPermission())) {
                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("no-permissions"));
                return false;
            }
            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("give")) {
                    String petString = args[2].toLowerCase();

                    if(Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("invalid-player"));
                        return false;
                    } else {
                        if(!Bukkit.getPlayer(args[1]).isOnline()) {
                            player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("invalid-player"));
                            return false;
                        }
                    }

                    if(PetType.getPetTypeFromClassName(petString) == null) {
                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("invalid-pet"));
                        return false;
                    }

                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    PetType petType = PetType.getPetTypeFromClassName(petString);

                    for(PetData petData : PlayerPetManager.getPlayers().get(targetPlayer).getPets()) {
                        if(petData.getPetType().equalsIgnoreCase(petString)) {
                            player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("already-owns-pet"));
                            return false;
                        }
                    }

                    PlayerPetManager.getPlayers().get(targetPlayer).getPets().add(new PetData(player, petType.getClassName(), petType.getClassName(), null, false, false));
                    try {
                        PlayerPetManager.getPlayers().get(targetPlayer).savePlayerData();

                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("give-success").replace("{0}", targetPlayer.getName()).replace("{1}", petType.getClassName()));
                        targetPlayer.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("give-success-receiver").replace("{0}", player.getName()).replace("{1}", petType.getClassName()));

                        PlayerPetManager.getPlayers().get(targetPlayer).getPetGUI().buildInventories();
                    } catch (IOException e) {
                        player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("error-message"));
                        e.printStackTrace();
                    }
                } else
                    player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("wrong-syntax"));
            } else
                player.sendMessage(Pets.getInstance().getPrefix() + Pets.getInstance().getLanguageLoader().getTranslationByKey("wrong-syntax"));
        }

        return true;
    }
}
