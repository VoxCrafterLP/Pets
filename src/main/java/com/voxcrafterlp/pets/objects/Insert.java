package com.voxcrafterlp.pets.objects;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 02.12.2020
 * Time: 10:34
 * Project: Pets
 */

public class Insert {

    private String row;
    private String value;

    public Insert(String row, String value) {
        this.row = row;
        this.value = value;
    }

    public String getRow() {
        return row;
    }

    public String getValue() {
        return value;
    }
}
