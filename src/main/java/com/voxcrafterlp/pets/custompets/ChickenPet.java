package com.voxcrafterlp.pets.custompets;

import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.objects.PetData;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 06.11.2020
 * Time: 10:08
 * For Project: Pets
 */

public class ChickenPet extends CustomPet {

    private Player player;
    private PetData petData;
    private Entity entity;

    public void set(PetData petData) {
        this.petData = petData;
        this.player = this.petData.getOwner();
        this.entity = this.spawn();
    }

    public CustomPet getPet() {
        return this;
    }

    public PetData getPetData() {
        return this.petData;
    }

    public PetType getPetType() {
        return PetType.CHICKEN;
    }

    public int getPrice() {
        return 1000; //CONFIG
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Entity spawn() {
        Chicken chicken = (Chicken) this.petData.getLocation().getWorld().spawnEntity(this.petData.getLocation(), EntityType.CHICKEN);
        chicken.setAdult();
        chicken.setCustomName(this.petData.getDisplayMame());
        chicken.setCustomNameVisible(true);
        chicken.setCanPickupItems(false);

        return chicken;
    }

    public void onTick() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if(this.entity.getLocation().distance(players.getLocation()) <= 10) {
                players.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100, 0, true, false));
            }
        });
        if(!this.petData.isSitting()) {
            if(this.entity.getLocation().distance(this.player.getLocation()) > 25)
                this.entity.teleport(this.player.getLocation());
            if(this.entity.getLocation().distance(player.getLocation()) > 5.0)
                followPlayer();
        }
    }

    public void updateSitting() {
        this.setAI(this.entity, !this.petData.isSitting());
    }

    private void followPlayer() {
        ((EntityInsentient) ((CraftEntity) entity).getHandle()).getNavigation().a(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1F);
    }

    public void remove() {
        this.entity.remove();
    }

    private void setAI(Entity entity, boolean ai) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if(tag == null)
            tag = new NBTTagCompound();
        nmsEntity.c(tag);
        if(ai)
            tag.setInt("NoAI", 0);
        else
            tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
}