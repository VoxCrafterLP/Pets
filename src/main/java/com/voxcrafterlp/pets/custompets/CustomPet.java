package com.voxcrafterlp.pets.custompets;

import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.objects.PetData;
import org.bukkit.entity.Entity;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 10:05
 * For Project: Pets
 */

public abstract class CustomPet {

    public abstract void set(PetData petData);
    public abstract CustomPet getPet();
    public abstract PetData getPetData();
    public abstract PetType getPetType();
    public abstract int getPrice();
    public abstract Entity getEntity();
    public abstract Entity spawn();
    public abstract void onTick();
    public abstract void updateSitting();
    public abstract void remove();

}
