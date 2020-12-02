package com.voxcrafterlp.pets.objects;

import com.voxcrafterlp.pets.enums.RowType;

/**
 * This file was created by VoxCrafter_LP!
 * Date: 02.12.2020
 * Time: 10:34
 * Project: Pets
 */

public class Row {

    private String name;
    private RowType type;

    public Row(String name, RowType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public RowType getType() {
        return type;
    }
}
