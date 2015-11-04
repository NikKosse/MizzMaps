package com.example.derek.teamb;

import com.Models.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class uses the A* pathfinding algorithm to find an optimal path between two locations on the map
 */
public class Pathfinder {

    private ArrayList<Node> nodes; //stores all nodes for the current building (or floor) being searched
    private HashMap<Long, Integer> idMap; //maps node Id's to the proper index in this.nodes
    private PriorityQueue<Node> fringe; //current nodes being considered for exploration
    private Set<Node> closedSet; //keeps track of nodes that have already been explored so we don't backtrack
    private Node current; // node currently being analyzed

    /**
     * Create a Pathfinder class for searching a building
     * @param buildingID integer id of the building to be searched
     */
    public Pathfinder(int buildingID){
        fringe = new PriorityQueue<>(25, Node.nodeComparator);
        closedSet = new HashSet<>();  //TODO: research performance for hashsets and priorityqueues; maybe specify max capacity?
        idMap = new HashMap<>();

        //TODO : database query to select all of the nodes for the building parameter
        //Only will need to get the nodes with floors in between (inclusive) the start and end floor
            //Load the results of the query into this.nodes
            //for each node which is inserted into this.nodes, execute the following line:
            //  this.idMap.put(nodeID, indexInArrayList);
    }

    /**
     * Searches between two nodes in a building
     *
     * This algorithm assumes that the constructor was successful in querying for the nodes in the proper
     *   building / floor as well as creating the fringe, closed set, and map from node id's to indexes
     * Note: This algorithm currently only works for a single building.  It will have to be called
     *   multiple times in order to search between separate buildings.
     *
     * @param startNode integer id of the beginning node
     * @param goalNode integer id of the goal node
     * @return an ArrayList of the long id's of the nodes along the optimal path
     */
    public ArrayList<Long> search(int startNode, int goalNode){

        fringe.add(nodes.get(idMap.get(Long.valueOf(startNode))));
        while ( ! fringe.isEmpty()){
            current = fringe.remove();
            if (closedSet.contains(current)) {
                continue; //don't expand nodes we've already expanded
            }
            if (current.getNode_id() == goalNode) {
                //TODO: Do we somehow want to return the total distance traveled as well?
                return getPath(current);
            }
            expand(current);
        }

        //if we finish the while loop without finding the goal, it must be unreachable / in another building
        return null;
    }

    /**
     * Utility method called by search, which inserts adjacent nodes into fringe with proper priority
     *
     * @param nodeToExpand node currently being searched for adjacents
     */
    private void expand(Node nodeToExpand) {
        //TODO: will nodes in the fringe ever be overwritten with a worse priority before they're closed? Make sure on this...
        int costUntilNow, priority;
        Node nodeToInsert;
        for (int i=0; i < nodeToExpand.getReachable_nodes().size(); i++) {
            nodeToInsert = nodes.get( idMap.get(nodeToExpand.getReachable_nodes().get(i)));
            costUntilNow = nodeToExpand.getCostFromPrev() + nodeToExpand.getDistances().get(i);
            priority = (int) Math.round( costUntilNow + Math.sqrt(
                    Math.pow(nodeToExpand.getX() - nodeToInsert.getX(), 2)
                    + Math.pow(nodeToExpand.getY() - nodeToInsert.getY(), 2)
                    + Math.pow((nodeToExpand.getFloor() - nodeToInsert.getFloor())*12, 2)
            ));
            nodeToInsert.setPrevNode(nodeToExpand);
            nodeToInsert.setCostFromPrev(costUntilNow);
            nodeToInsert.setPriority(priority);
            fringe.add(nodeToInsert);
        }
        closedSet.add(nodeToExpand);
    }

    /**
     * Utility method called by search, returns the list of nodes traversed on the optimal path to the goal
     *
     * @param currentNode node at the end of the current path being analyzed
     * @return an ArrayList of the long id's of the nodes along the optimal path
     */
    private ArrayList<Long> getPath(Node currentNode) {
        ArrayList<Long> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.getNode_id());
            currentNode = currentNode.getPrevNode();
        }
        Collections.reverse(path);
        return path;
    }
}
