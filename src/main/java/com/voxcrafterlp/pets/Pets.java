package com.voxcrafterlp.pets;

import com.voxcrafterlp.pets.commands.PetCommand;
import com.voxcrafterlp.pets.config.LanguageLoader;
import com.voxcrafterlp.pets.config.PetsConfig;
import com.voxcrafterlp.pets.gui.InventoryClickListener;
import com.voxcrafterlp.pets.listener.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 09:45
 * For Project: Pets
 */

@Getter
public class Pets extends JavaPlugin {

    private static Pets instance;
    private File file;

    private PetsConfig petsConfig;
    private LanguageLoader languageLoader;

    public void onEnable() {
        instance = this;

        if(!checkForVault()) return;

        this.loadCommands();
        this.loadListener();

        try {
            this.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerInteractAtEntityListener(), this);
        pluginManager.registerEvents(new PlayerChangedWorldListener(), this);
    }

    private void loadConfig() throws IOException {
        this.loadLanguages();
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        this.file = new File("plugins/Pets/playerdata.json");
        try {
            if(this.file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(this.file);
                fileWriter.write("{}");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.petsConfig = new PetsConfig();
        new File("plugins/Pets/languages/").mkdir();
        this.languageLoader = new LanguageLoader("plugins/Pets/languages/" + this.petsConfig.getLanguageFile() + ".lang");
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

    private void loadLanguages() {
        Pets.getInstance().saveResource("languages/US_en.lang", false);
    }

    public String getPrefix() { return "§8[§cPets§8]§7 "; }

    public static Pets getInstance() { return instance; }
}
