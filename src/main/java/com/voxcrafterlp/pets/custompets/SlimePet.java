package com.voxcrafterlp.pets.custompets;

import com.voxcrafterlp.pets.Pets;
import com.voxcrafterlp.pets.enums.PetType;
import com.voxcrafterlp.pets.objects.PetData;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 15.11.2020
 * Time: 13:56
 * Project: Pets
 */

public class SlimePet extends CustomPet{

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
        return PetType.SLIME;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Entity spawn() {
        Slime slime = (Slime) this.petData.getLocation().getWorld().spawnEntity(this.petData.getLocation(), EntityType.SLIME);
        slime.setSize(1);
        slime.setCustomName(this.petData.getDisplayMame());
        slime.setCustomNameVisible(true);
        slime.setCanPickupItems(false);

        return slime;
    }

    public void onTick() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if(this.entity.getLocation().distance(players.getLocation()) <= Pets.getInstance().getPetsConfig().getPetRadius()) {
                players.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0, true, false));
            }
        });
        if(!this.petData.isSitting()) {
            if(this.entity.getLocation().distance(this.player.getLocation()) > 25 && this.player.isOnGround())
                this.entity.teleport(this.player.getLocation());
            if(this.entity.getLocation().distance(player.getLocation()) > 5.0)
                followPlayer();
        } else {
            Location particleLocation = this.entity.getLocation().clone();
            particleLocation.setY(particleLocation.getY() + 0.8);
            player.playEffect(particleLocation, Effect.INSTANT_SPELL, 12);
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
