package com.example.derek.teamb;

/**
 * Class to hold the information from the room table in the database.
 */
public class Room {

    public Room() {
    }


    public Room(int roomNumber, int roomNode, int roomFloor, int building_ID, String roomType, int[] coordinates) {
        this.roomNumber = roomNumber;
        this.roomNode = roomNode;
        this.roomFloor = roomFloor;
        this.building_ID = building_ID;
        this.roomType = roomType;
        this.coordinates = coordinates;
    }


    private int roomNumber;
    private int roomNode;
    private int roomFloor;
    private int building_ID;
    private String roomType;
    //maybe have this value?
    private int[] coordinates = new int[2];


    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNode() {
        return roomNode;
    }

    public void setRoomNode(int roomNode) {
        this.roomNode = roomNode;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public int getBuilding_ID() {
        return building_ID;
    }

    public void setBuilding_ID(int building_ID) {
        this.building_ID = building_ID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}
