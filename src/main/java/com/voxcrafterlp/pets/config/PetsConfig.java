package com.voxcrafterlp.pets.config;

import com.voxcrafterlp.pets.enums.PetType;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 13.11.2020
 * Time: 16:06
 * For Project: Pets
 */

@Getter
public class PetsConfig {

   private final List<String> disabledWorlds;
   private final int petRadius;
   private final boolean petInvulnerability, randomColor;
   private final String languageFile;

   private final File configFile;
   private final YamlConfiguration configuration;

   private final HashMap<String, Boolean> enabledPets;
   private final HashMap<String, Integer> petPrices;

   private final String adminPermission;

   private final boolean databaseEnabled;
   private final String databaseHost;
   private final String databaseUsername;
   private final String databasePassword;
   private final String databaseName;
   private final int databasePort;

    /**
     * Loads and caches the settings from the config.yml file
     */
   public PetsConfig() {
        this.configFile = new File("plugins/Pets/config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.configFile);

        this.disabledWorlds = this.configuration.getStringList("worlds-disabled");
        this.petRadius = this.configuration.getInt("pet-radius");
        this.petInvulnerability = this.configuration.getBoolean("pet-invulnerable");
        this.randomColor = this.configuration.getBoolean("random-color");
        this.languageFile = this.configuration.getString("language-file");
        this.adminPermission = this.configuration.getString("admin-permission");


        this.databaseEnabled = this.configuration.getBoolean("mysql-enabled");
        this.databaseHost = this.configuration.getString("mysql-hostname");
        this.databaseUsername = this.configuration.getString("mysql-username");
        this.databasePassword = this.configuration.getString("mysql-password");
        this.databaseName = this.configuration.getString("mysql-database");
        this.databasePort = this.configuration.getInt("mysql-port");

        this.enabledPets = new HashMap<>();
        this.petPrices = new HashMap<>();
        for(PetType petTypes: PetType.values()) {
            this.enabledPets.put(petTypes.getClassName(), this.configuration.getBoolean("pets." + petTypes.getClassName() + ".enabled"));
            this.petPrices.put(petTypes.getClassName(), this.configuration.getInt("pets." + petTypes.getClassName() + ".price"));
        }
   }

}
