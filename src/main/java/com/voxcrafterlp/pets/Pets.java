package com.voxcrafterlp.pets;

import com.voxcrafterlp.pets.commands.PetCommand;
import com.voxcrafterlp.pets.gui.InventoryClickListener;
import com.voxcrafterlp.pets.listener.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 09:45
 * For Project: Pets
 */

public class Pets extends JavaPlugin {

    private static Pets instance;
    @Getter private File file;

    public void onEnable() {
        instance = this;

        if(!checkForVault()) return;

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        this.file = new File("plugins/Pets/playerdata.json");
        try {
            if(this.file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("{}");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.loadCommands();
        this.loadListener();
    }

    private void loadCommands() {
        getCommand("pet").setExecutor(new PetCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new ItemSpawnListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new PlayerInteractAtEntityListener(), this);
    }

    private boolean checkForVault() {
        Bukkit.getConsoleSender().sendMessage("§8===========================================");
        Bukkit.getConsoleSender().sendMessage("§7Checking for §aVault§7...");
        Bukkit.getConsoleSender().sendMessage(" ");

        if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("§cVault not found!");
            Bukkit.getConsoleSender().sendMessage("§cDisabling the plugin");
            Bukkit.getConsoleSender().sendMessage("§8===========================================");

            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        Bukkit.getConsoleSender().sendMessage("§aVault found! Starting up...");
        Bukkit.getConsoleSender().sendMessage("§8===========================================");
        return true;
    }

    public String getPrefix() { return "§8[§cPets§8]§7 "; }

    public static Pets getInstance() { return instance; }
}
