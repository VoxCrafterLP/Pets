package com.voxcrafterlp.pets.manager;

import com.google.common.collect.Lists;
import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.custompets.CustomPet;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.gui.PetGUI;
import com.voxcrafterlp.pets.objects.PetData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 08.11.2020
 * Time: 12:03
 * For Project: Pets
 */

@Getter
public class PlayerPetManager {

    private static final HashMap<Player, PlayerPetManager> players = new HashMap<>();

    private final Player player;
    private final PetGUI petGUI;
    private final List<PetData> pets;
    private int schedulerID;
    private CustomPet spawnedPet;

    public PlayerPetManager(Player player) {
        this.player = player;

        this.pets = Lists.newCopyOnWriteArrayList();
        try {
            this.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        players.put(player, this);
        this.petGUI = new PetGUI(player);
    }

    private void loadData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Pets.getInstance().getFile()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            JSONObject jsonObject = new JSONObject(line);

            if(jsonObject.isNull(player.getUniqueId().toString())) {
                jsonObject.put(player.getUniqueId().toString(), new JSONObject().put("pets", new JSONArray()));

                new FileWriter("plugins/Pets/playerdata.json", false).close();
                FileWriter fileWriter = new FileWriter(Pets.getInstance().getFile());
                fileWriter.write(jsonObject.toString());
                fileWriter.close();

                return;
            }

            if(jsonObject.getJSONObject(player.getUniqueId().toString()).getJSONArray("pets").length() == 0) return;

            for(int i = 0; i< jsonObject.getJSONObject(player.getUniqueId().toString()).getJSONArray("pets").length(); i++) {
                JSONObject pet = jsonObject.getJSONObject(player.getUniqueId().toString()).getJSONArray("pets").getJSONObject(i);
                this.pets.add(new PetData(player, pet.getString("type"), pet.getString("name"), null, false, pet.getBoolean("enabled")));
            }
        }
    }

    public void savePlayerData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Pets.getInstance().getFile()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {

            if(isValidJsSON(line)) {
                JSONObject jsonObject = new JSONObject(line);
                JSONArray jsonArray = new JSONArray();

                this.pets.forEach(petData -> {
                    jsonArray.put(new JSONObject().put("type", petData.getPetType()).put("name", petData.getDisplayMame()).put("enabled", petData.isEnabled()));
                });

                jsonObject.remove(player.getUniqueId().toString());
                jsonObject.put(player.getUniqueId().toString(), new JSONObject().put("pets", jsonArray));

                new FileWriter("plugins/Pets/playerdata.json", false).close();
                FileWriter fileWriter = new FileWriter(Pets.getInstance().getFile());
                fileWriter.write(jsonObject.toString());
                fileWriter.close();
            }
        }
    }

    public void delete() throws IOException {
        this.despawnPet();
        this.savePlayerData();
        players.remove(player);
    }

    private boolean isValidJsSON(String string) {
        try {
            new JSONObject(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void spawnPet(PetData petData) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        PetType petType = PetType.getPetTypeFromClassName(petData.getPetType());
        Class<CustomPet> clazz = (Class<CustomPet>) Class.forName("com.voxcrafterlp.pets.custompets." + petType.getClassName());

        CustomPet customPet = clazz.newInstance();
        customPet.set(petData);

        this.spawnedPet = customPet;
        this.getPets().forEach(pet -> {
            if(pet.getPetType().equalsIgnoreCase(petData.getPetType())) {
                pet.setEnabled(true);
                pet.setSitting(false);
            }
        });

        schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Pets.getInstance(), customPet::onTick, 10, 4);
    }

    public void despawnPet() {
        if(this.spawnedPet != null) {
            Bukkit.getScheduler().cancelTask(this.schedulerID);
            this.spawnedPet.remove();
            this.spawnedPet = null;
        }
    }

    public static HashMap<Player, PlayerPetManager> getPlayers() { return players; }
}
