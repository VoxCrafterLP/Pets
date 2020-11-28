package com.voxcrafterlp.pets.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 13.11.2020
 * Time: 16:15
 * Project: Pets
 */

@Getter
public class LanguageLoader {

    private final File languageFile;
    private final YamlConfiguration configuration;

    /**
     * Loads the language from the config file
     * @param path Path and filename
     */
    public LanguageLoader(String path) {
        this.languageFile = new File(path);
        this.configuration = YamlConfiguration.loadConfiguration(this.languageFile);
    }

    /**
     * Gets a translation from the language file
     * @param key Key of translation
     * @return Translated string
     */
    public String getTranslationByKey(String key) {
        return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(key));
    }

    public String[] buildDescription(String descriptionString) {
        List<String> lore = Lists.newCopyOnWriteArrayList();
        lore.addAll(Arrays.asList("§8§m------------------", " "));
        lore.addAll(Arrays.asList(descriptionString.split("\n")));
        lore.addAll(Arrays.asList(" ", "§8§m------------------"));
        return lore.toArray(new String[0]);
    }
}
