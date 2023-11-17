package com.kevin.pettheshirley;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

public final class PetTheShirley extends JavaPlugin {

    private JDA client;
    public DataFile dataFile;
    public static PetTheShirley self;
    @Getter
    public HashMap<String, Player> IDtoPlayerMap = new HashMap<>();
    @Getter
    public ArrayList<String> deathMessages = new ArrayList<>();

    @Override
    public void onEnable() {
        String TOKEN = "MTE3NDkyODEyNDc2OTc0NzAxNA.Gd8EEs.RuMlDtWvAhs6jKapsDxHCjVkCIqa5xxkE2fGyw";
        JDABuilder builder = JDABuilder.createDefault(TOKEN)
                .enableIntents(EnumSet.allOf(GatewayIntent.class))
                .addEventListeners(new Listeners())
                .setActivity(Activity.watching("you suffer"));
        try {
            client = builder.build();
        } catch (InvalidTokenException e) {
            Bukkit.getLogger().warning("[PetTheShirley] BOT-TOKEN is invalid");
            e.printStackTrace();
        }
        dataFile = new DataFile(this);
        self = this;
        deathMessages.add("Shirley is tired of your petting so she phones you to death");
        deathMessages.add("Shirley stabs you 28 times. You bleed to death");
        deathMessages.add("Shirley kicks you in the back. Your spine breaks and you die");
    }

    @Override
    public void onDisable() {
        try {
            saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData() throws IOException {
        for (String ID : IDtoPlayerMap.keySet()) {
            int bank = IDtoPlayerMap.get(ID).getBank();
            int wallet = IDtoPlayerMap.get(ID).getWallet();
            dataFile.getConfig().set(ID + ".bank", bank);
            dataFile.getConfig().set(ID + ".wallet", wallet);
        }
        dataFile.saveConfig();
    }

    /*
    true: player data successfully loaded
    false: player does not exist
    */
    public void loadDataFromID(String ID) {
        int bank = dataFile.getConfig().getInt(ID + ".bank");
        int wallet = dataFile.getConfig().getInt(ID + ".wallet");
        Player player = new Player(bank, wallet);
        IDtoPlayerMap.put(ID, player);
    }

    public static PetTheShirley getInstance() {
        return self;
    }

}
