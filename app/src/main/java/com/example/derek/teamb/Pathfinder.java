package com.example.derek.teamb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class uses the A* pathfinding algorithm to find an optimal path between two locations on the map
 */
public class Pathfinder {

    private ArrayList<Node> nodes; //stores all nodes for the current building being searched
    private HashMap<Integer, Integer> idMap; //maps node Id's to the proper index in this.nodes
    private PriorityQueue<Node> fringe; //current nodes being considered for exploration
    private Set<Node> closedSet; //keeps track of nodes that have already been explored so we don't backtrack
    private Node current; // node currently being analyzed

    public Pathfinder(int buildingID){
        fringe = new PriorityQueue<>(25, Node.nodeComparator);
        closedSet = new HashSet<>();  //TODO: research performance for hashsets and priorityqueues; maybe specify max capacity?
        idMap = new HashMap<>();

        //TODO : database query to select all of the nodes for the building parameter
        //Load the results of the query into this.nodes
        //for each node which is inserted into this.nodes, execute the following line:
        //  this.idMap.put(nodeID, indexInArrayList);
    }

    /**
     * This does most of the heavy lifting of the search algorithm
     * It assumes that the constructor was successful in querying for the nodes in the proper building / floor
     *      as well as creating the fringe, closed set, and map from node id's to indexes
     */
    public ArrayList<Node> search(int startNode, int goalNode){
        //TODO: decide what we want this function to return.  List of nodeIDs / node objects? Total cost?

        fringe.add(nodes.get(idMap.get(Integer.valueOf(startNode))));
        while ( ! fringe.isEmpty()){
            current = fringe.remove();
            if (closedSet.contains(current)) {
                continue; //don't expand nodes we've already expanded
            }
            if (current.getId() == goalNode) {
                return getPath(current);
            }
            expand(current);
        }

        //TODO: extend this algorithm to work for multiple floors and for separate buildings on start/end node
        //maybe query for an additional floor once you come up against it

        //if we finish the while loop without finding the goal, it must be unreachable / in another building
        return null;
    }

    /**
     * Utility method called by search, which inserts adjacent nodes into fringe with proper priority
     */
    private void expand(Node nodeToExpand) {
        int costUntilNow, priority;
        Node nodeToInsert;
        for (int i=0; i < nodeToExpand.getAdjacents().size(); i++) {
            nodeToInsert = nodes.get( idMap.get(nodeToExpand.getAdjacents().get(i)));
            costUntilNow = nodeToExpand.getCostFromPrev() + nodeToExpand.getDistances().get(i);
            priority = (int) Math.round( costUntilNow + Math.sqrt(
                    Math.pow(nodeToExpand.getX() - nodeToInsert.getX(), 2)
                    + Math.pow(nodeToExpand.getY() - nodeToInsert.getY(), 2) ));
            nodeToInsert.setPrevNode(nodeToExpand);
            nodeToInsert.setCostFromPrev(costUntilNow);
            nodeToInsert.setPriority(priority);
            fringe.add(nodeToInsert);
        }
        closedSet.add(nodeToExpand);
    }

    /**
     * Utility method called by search, returns the list of nodes traversed on the optimal path to the goal
     */
    private ArrayList<Node> getPath(Node currentNode) {
        //TODO : maybe pick a better way of implementing this, depending on what we want to return from the search method
        if ( currentNode.getPrevNode() == null ) {
            ArrayList<Node> startOfPath = new ArrayList<>();
            startOfPath.add(currentNode);
            return startOfPath;
        }
        else {
            ArrayList<Node> firstPartOfPath = getPath(currentNode.getPrevNode());
            firstPartOfPath.add(currentNode);
            return firstPartOfPath;
        }
    }
}
