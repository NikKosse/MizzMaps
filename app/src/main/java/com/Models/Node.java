package com.Models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Course to hold the information from the room table in the database.
 *   used by the A* path finding algorithm to keep track of way-points in the graph / map
 */
public class Node {

    private long node_id;
    private ArrayList<Long> reachable_nodes;
    private ArrayList<Integer> distances;
    private int floor;
    private long buildingId;
    private double x;
    private double y;

    private int priority; //used when evaluating which node to explore next
    private Node prevNode;
    private int costFromPrev;

    public Node (){
    }

    public long getNode_id() {
        return node_id;
    }

    public void setNode_id (long node_id) {
        this.node_id = node_id;
    }

    public ArrayList<Long> getReachable_nodes() {
        return reachable_nodes;
    }

    public void setAdjacencies(String dbReachableNodes){
        if (dbReachableNodes.isEmpty()) return;
        ArrayList<Long> node_ids = new ArrayList<>();
        ArrayList<Integer> adjDistances = new ArrayList<>();
        String[] adjacents = dbReachableNodes.split(";");
        if (adjacents.length%2 == 0) {
            int i=0;
            while (i < adjacents.length - 1) {
                node_ids.add(Long.parseLong(adjacents[i].trim()));
                adjDistances.add(Integer.parseInt(adjacents[i + 1].trim()));
                i = i + 2;
            }
            setReachable_nodes(node_ids);
            setDistances(adjDistances);
        }
        else {
            //maybe log error or something?
        }
    }

    public void setReachable_nodes(ArrayList<Long> reachable_nodes) {
        this.reachable_nodes = reachable_nodes;
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

    public void setCoordinates(String coordinates){
        if (coordinates.isEmpty()) return;
        String[] coords = coordinates.split(";");
        if (coords.length == 2) {
            setX(Double.parseDouble(coords[0].trim()));
            setY(Double.parseDouble(coords[1].trim()));
        }
        else {
            //log an error or something?
        }
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

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
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
        return (int)node_id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Node) && ((Node) obj).getNode_id() == this.getNode_id();
    }
}