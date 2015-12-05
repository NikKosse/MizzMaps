package com.example.derek.teamb;

import android.content.Context;
import com.Models.Node;
import com.database.teamb.NodesDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class uses the A* pathfinding algorithm to find an optimal path between two locations on the map
 */
public class Pathfinder {

    private List<Node> nodes; //stores all nodes for the current building (or floor) being searched
    private HashMap<Long, Integer> idMap; //maps node Id's to the proper index in this.nodes
    private PriorityQueue<Node> fringe; //current nodes being considered for exploration
    private Set<Node> closedSet; //keeps track of nodes that have already been explored so we don't backtrack
    private Node current; // node currently being analyzed
    private Node goalNode;
    private NodesDAO nodesDAO;
    private int totalDistance;

    /**
     * Create a Pathfinder class for searching a building
     * @param context Android db context for loading the database
     */
    public Pathfinder(Context context){
        nodesDAO = new NodesDAO(context);
    }

    /**
     * Searches between two nodes in a building
     *
     * This algorithm assumes that the constructor was successful in querying for the nodes in the proper
     *   building / floor as well as creating the fringe, closed set, and map from node id's to indexes
     * Note: This algorithm currently only works for a single building.  It will have to be called
     *   multiple times in order to search between separate buildings.
     * Note: Once this method is run, the total path distance can be obtained by executing the
     *   getTotalDistance() method.
     *
     * @param startId long id of the beginning node
     * @param goalId long id of the goal node
     * @return an ArrayList of the long id's of the nodes along the optimal path
     */
    public List<Long> search(long startId, long goalId){

        //initialize data structures
        fringe = new PriorityQueue<>(25, Node.nodeComparator);
        closedSet = new HashSet<>();  //TODO: research performance for hashsets and priorityqueues; maybe specify max capacity?
        idMap = new HashMap<>();

        //get proper nodes
        List<Integer> startAndEndFloor = new ArrayList<>();
        long buildingID = nodesDAO.getBuildingAndFloors(startId, goalId, startAndEndFloor);
        if (startAndEndFloor.size() == 0 || buildingID == -1) {
            return null;
        }
        nodes = nodesDAO.getPathfinderNodes(buildingID, idMap, startAndEndFloor);
        goalNode = nodes.get(idMap.get(goalId));

        fringe.add(nodes.get(idMap.get(Long.valueOf(startId))));
        while ( ! fringe.isEmpty()){
            current = fringe.poll();
            if (current == goalNode) {
                setTotalDistance(current.getCostFromPrev());
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
        int costUntilNow, priority;
        Node nodeToInsert;
        ArrayList<Long> adjacents = nodeToExpand.getReachable_nodes();
        int numAdjacents = adjacents.size();
        int costFromPrev = nodeToExpand.getCostFromPrev();

        for (int i=0; i < numAdjacents; i++) {
            nodeToInsert = nodes.get( idMap.get(adjacents.get(i)));
            if (nodeToInsert != null && !closedSet.contains(nodeToInsert)){
                costUntilNow = costFromPrev + nodeToExpand.getDistances().get(i);
                priority = (int) Math.round( costUntilNow + Math.sqrt(
                        Math.pow(goalNode.getX() - nodeToInsert.getX(), 2)
                                + Math.pow(goalNode.getY() - nodeToInsert.getY(), 2)
                                + Math.pow((goalNode.getFloor() - nodeToInsert.getFloor())*12, 2)
                ));
                if (!fringe.contains(nodeToInsert)) {
                    nodeToInsert.setPrevNode(nodeToExpand);
                    nodeToInsert.setCostFromPrev(costUntilNow);
                    nodeToInsert.setPriority(priority);
                    fringe.add(nodeToInsert);
                }
                else if (priority < nodeToInsert.getPriority()) {
                    //Update these weights?  Would this ever happen?
                }
            }
        }
        closedSet.add(nodeToExpand);
    }

    /**
     * Utility method called by search, returns the list of nodes traversed on the optimal path to the goal
     *
     * @param currentNode node at the end of the current path being analyzed
     * @return an ArrayList of the long id's of the nodes along the optimal path
     */
    private List<Long> getPath(Node currentNode) {
        ArrayList<Long> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.getNode_id());
            currentNode = currentNode.getPrevNode();
        }
        Collections.reverse(path);
        return path;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    private void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }
}
