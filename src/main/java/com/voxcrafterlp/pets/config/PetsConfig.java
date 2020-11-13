package com.voxcrafterlp.pets.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
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
   private final boolean petInvulnerability;
   private final boolean randomColor;
   private final String languageFile;

   private final File configFile;
   private final YamlConfiguration configuration;

   public PetsConfig() {
        this.configFile = new File("plugins/Pets/config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.configFile);

        this.disabledWorlds = this.configuration.getStringList("worlds-disabled");
        this.petRadius = this.configuration.getInt("pet-radius");
        this.petInvulnerability = this.configuration.getBoolean("pet-invulnerable");
        this.randomColor = this.configuration.getBoolean("random-color");
        this.languageFile = this.configuration.getString("language-file");
   }

   public void save() {
       try {
           this.configuration.save(this.configFile);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
