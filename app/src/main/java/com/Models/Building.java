package com.Models;

import java.util.PriorityQueue;

/**
 * Building model: contains all info on a building
 */
public class Building {
    private long building_id;
    private String building_name;

    public Building() {
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }
}
