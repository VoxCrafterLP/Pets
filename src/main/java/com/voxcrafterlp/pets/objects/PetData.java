package com.voxcrafterlp.pets.objects;

import lombok.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 10:19
 * For Project: Pets
 */

@Getter @Setter
public class PetData {

    private Player owner;
    private String petType;
    private String displayMame;
    private Location location;
    private boolean sitting;
    private boolean enabled;

    public PetData(Player owner, String petType, String displayMame, Location location, boolean sitting, boolean enabled) {
        this.owner = owner;
        this.petType = petType;
        this.displayMame = displayMame;
        this.location = location;
        this.sitting = sitting;
        this.enabled = enabled;
    }
}
