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
            if(!player.hasPermission("pets.admin")) {
                player.sendMessage(Pets.getInstance().getPrefix() + "§7You §care not permitted §7to execute this command!");
                return false;
            }
            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("give")) {
                    /*try {
                        PlayerPetManager.getPlayers().get(player).spawnPet(new PetData(player, "ChickenPet", "§cMoin Meista", player.getLocation(), false, true));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }

                    player.sendMessage("test");*/

                    String petString = args[2].toLowerCase();

                    if(Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(Pets.getInstance().getPrefix() + "§cInvalid player!");
                        return false;
                    } else {
                        if(!Bukkit.getPlayer(args[1]).isOnline()) {
                            player.sendMessage(Pets.getInstance().getPrefix() + "§cThe player has to be online!");
                            return false;
                        }
                    }

                    if(PetType.getPetTypeFromClassName(petString) == null) {
                        player.sendMessage(Pets.getInstance().getPrefix() + "§cInvalid pet! §7Valid pets are§8: §aChickenPet§8, §aCatPet§8, §aWolfPet§8, §aSlimePet§8, §aSquidPet§8, §aRabbitPet");
                        return false;
                    }

                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    PetType petType = PetType.getPetTypeFromClassName(petString);

                    for(PetData petData : PlayerPetManager.getPlayers().get(targetPlayer).getPets()) {
                        if(petData.getPetType().equalsIgnoreCase(petString)) {
                            player.sendMessage(Pets.getInstance().getPrefix() + "§cThis player already has this pet!");
                            return false;
                        }
                    }

                    PlayerPetManager.getPlayers().get(targetPlayer).getPets().add(new PetData(player, petType.getClassName(), petType.getClassName(), null, false, false));
                    try {
                        PlayerPetManager.getPlayers().get(targetPlayer).savePlayerData();

                        player.sendMessage(Pets.getInstance().getPrefix() + "§a" + targetPlayer.getName() + " §7received a §a" + petType.getClassName() + "§7.");
                        targetPlayer.sendMessage(Pets.getInstance().getPrefix() + "§a" + player.getName() + " §7gave you a §a" + petType.getClassName() + "§7.");

                        PlayerPetManager.getPlayers().get(targetPlayer).getPetGUI().buildInventories();
                    } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                        player.sendMessage(Pets.getInstance().getPrefix() + "§cSomething went wrong! Please read the logs for more information!");
                        e.printStackTrace();
                    }
                } else
                    player.sendMessage(Pets.getInstance().getPrefix() + "§cWrong syntax! §7Please use §c/pet give <player> <pet> §7or §c/pet");
            } else
                player.sendMessage(Pets.getInstance().getPrefix() + "§cWrong syntax! §7Please use §c/pet give <player> <pet> §7or §c/pet");
        }

        return true;
    }
}
