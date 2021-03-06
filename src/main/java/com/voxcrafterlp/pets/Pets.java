package com.voxcrafterlp.pets;

import com.voxcrafterlp.pets.commands.PetCommand;
import com.voxcrafterlp.pets.config.LanguageLoader;
import com.voxcrafterlp.pets.config.PetsConfig;
import com.voxcrafterlp.pets.database.Database;
import com.voxcrafterlp.pets.database.DatabaseAdapter;
import com.voxcrafterlp.pets.enums.RowType;
import com.voxcrafterlp.pets.gui.InventoryClickListener;
import com.voxcrafterlp.pets.listener.*;
import com.voxcrafterlp.pets.objects.Insert;
import com.voxcrafterlp.pets.objects.Row;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
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

    private Economy economy;

    private Database mysqlDatabase;
    private DatabaseAdapter databaseAdapter;

    /**
     * Plugin startup and initializes the main class
     */
    public void onEnable() {
        instance = this;

        if(!checkForVault()) return;

        this.loadCommands();
        this.loadListener();

        this.loadConfig();
        this.loadDatabase();
    }

    public void onDisable() {
        if(this.getPetsConfig().isDatabaseEnabled())
            this.mysqlDatabase.disconnect();
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
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
    }

    /**
     * Loads the default languages into the languages folder and
     * loads the settings from the config.yml file
     */
    private void loadConfig() {
        this.loadLanguages();
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        this.petsConfig = new PetsConfig();
        new File("plugins/Pets/languages/").mkdir();
        this.languageLoader = new LanguageLoader("plugins/Pets/languages/" + this.petsConfig.getLanguageFile() + ".yml");
    }

    private void loadPlayerData() {
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
    }

    /**
     * Checks if the Vault plugin is installed. If so, this method gets loads the economy of the server
     * @return Returns if the Vault plugin is installed and working properly
     */
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

        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(registeredServiceProvider == null) {
            Bukkit.getConsoleSender().sendMessage("§cAn error occurred while initializing Vault...");
            Bukkit.getConsoleSender().sendMessage("§cPlease check the configuration of your money plugin!");
            Bukkit.getConsoleSender().sendMessage("§8===========================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        this.economy = registeredServiceProvider.getProvider();
        if(this.economy == null) {
            Bukkit.getConsoleSender().sendMessage("§cAn error occurred while initializing Vault...");
            Bukkit.getConsoleSender().sendMessage("§cPlease check the configuration of your money plugin!");
            Bukkit.getConsoleSender().sendMessage("§8===========================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        Bukkit.getConsoleSender().sendMessage("§aVault found! Starting up...");
        Bukkit.getConsoleSender().sendMessage("§8===========================================");
        return true;
    }

    /**
     * Loads the default languages into the languages folder
     */
    private void loadLanguages() {
        Pets.getInstance().saveResource("languages/en_US.yml", false);
        Pets.getInstance().saveResource("languages/de_DE.yml", false);
        Pets.getInstance().saveResource("languages/de_CH.yml", false);
        Pets.getInstance().saveResource("languages/lu_LU.yml", false);
    }

    /**
     * Connects to the database if enabled int the config
     */
    private void loadDatabase() {
        if(this.getPetsConfig().isDatabaseEnabled()) {
            Bukkit.getConsoleSender().sendMessage("§aMySQL enabled. Connecting...");
            this.mysqlDatabase = new Database(this.getPetsConfig().getDatabaseHost(),
                    this.getPetsConfig().getDatabaseUsername(), this.getPetsConfig().getDatabasePassword(),
                    this.getPetsConfig().getDatabaseName(), this.getPetsConfig().getDatabasePort());
            this.mysqlDatabase.connect();
            this.databaseAdapter = new DatabaseAdapter(this.mysqlDatabase);
            this.databaseAdapter.createTable("pets", new Row("PETDATA", RowType.TEXT));

            if(!this.databaseAdapter.containsEntry("pets"))
                this.databaseAdapter.insertIntoTable("pets", new Insert("PETDATA", "{}"));
        } else
            this.loadPlayerData();
    }

    public String getPrefix() { return this.getLanguageLoader().getTranslationByKey("prefix"); }

    public static Pets getInstance() { return instance; }
}
