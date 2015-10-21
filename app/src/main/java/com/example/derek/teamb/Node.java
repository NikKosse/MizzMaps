package com.example.derek.teamb;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class to hold the information from the room table in the database.
 *   used by the A* path finding algorithm to keep track of way-points in the graph / map
 */
public class Node {

    private int id;
    private ArrayList<Integer> adjacents; //TODO: is there a better way to store our adjacent + distance pairs?
    private ArrayList<Integer> distances;
    private int floor;
    private int buildingId;  //TODO: Do we need this in our object? I don't know if it's really necessary here...
    private double x;
    private double y;

    private int priority; //used when evaluating which node to explore next
    private Node prevNode;
    private int costFromPrev;

    public Node (int nodeID, ArrayList<Integer> adj, ArrayList<Integer> dist, int floor, int building, double xCoord, double yCoord){
        this.id = nodeID;
        this.adjacents = adj;
        this.distances = dist;
        this.floor = floor;
        this.buildingId = building;
        this.x = xCoord;
        this.y = yCoord;
        this.prevNode = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(ArrayList<Integer> adjacents) {
        this.adjacents = adjacents;
    }

    public ArrayList<Integer> getDistances() {
        return distances;
    }

    public void setDistances(ArrayList<Integer> distances) {
        this.distances = distances;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public int getCostFromPrev() {
        return costFromPrev;
    }

    public void setCostFromPrev(int costFromPrev) {
        this.costFromPrev = costFromPrev;
    }

    public static Comparator<Node> nodeComparator = new Comparator<Node>(){
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.priority - n2.priority);
        }
    };

    //Following two methods are necessary for determining if two nodes are the same

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Node) && ((Node) obj).getId() == this.getId();
    }
}