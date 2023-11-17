package com.kevin.pettheshirley;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataFile {

    public final PetTheShirley main;

    private FileConfiguration dataConfig;
    private File configFile;

    public DataFile(PetTheShirley main) {
        this.main = main;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(main.getDataFolder(), "data.yml");
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = main.getResource("data.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) reloadConfig();
        return dataConfig;
    }

    public void saveConfig() throws IOException {
        this.getConfig().save(configFile);
    }

    public void saveDefaultConfig() {
        if (configFile == null)
            configFile = new File(main.getDataFolder(), "data.yml");
        if (!configFile.exists())
            main.saveResource("data.yml", false);
    }
}

