package com.voxcrafterlp.pets.config;

import com.voxcrafterlp.pets.enums.PetType;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
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

   public PetsConfig() {
        this.configFile = new File("plugins/Pets/config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.configFile);

        this.disabledWorlds = this.configuration.getStringList("worlds-disabled");
        this.petRadius = this.configuration.getInt("pet-radius");
        this.petInvulnerability = this.configuration.getBoolean("pet-invulnerable");
        this.randomColor = this.configuration.getBoolean("random-color");
        this.languageFile = this.configuration.getString("language-file");

        this.enabledPets = new HashMap<>();
        this.petPrices = new HashMap<>();
        for(PetType petTypes: PetType.values()) {
            this.enabledPets.put(petTypes.getClassName(), this.configuration.getBoolean("pets." + petTypes.getClassName() + ".enabled"));
            this.petPrices.put(petTypes.getClassName(), this.configuration.getInt("pets." + petTypes.getClassName() + ".price"));
        }
   }

   public void save() {
       try {
           this.configuration.save(this.configFile);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
