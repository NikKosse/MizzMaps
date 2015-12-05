package com.example.derek.teamb;

import android.test.AndroidTestCase;
import android.content.Context;
import com.Models.Node;
import java.util.ArrayList;
import java.util.List;

public class PathfinderTest extends AndroidTestCase {

    private Context context;

    public void setUp() throws Exception {
        super.setUp();
        context = getContext();
        assertNotNull(context);

    }

    public void testSearch() throws Exception {
        Pathfinder pf = new Pathfinder(context);
        List<Node> returnedPath, desiredPath;

        //TODO: Find actual distances of paths and verify path node id's

        //TEST SEARCH 1

        returnedPath = pf.search(54, 40);

        //make sure path is the right path: 54, 46, 45, 42, 41, 40
        Node node54 = new Node(); node54.setNode_id(54);
        Node node46 = new Node(); node46.setNode_id(46);
        Node node45 = new Node(); node45.setNode_id(45);
        Node node42 = new Node(); node42.setNode_id(42);
        Node node41 = new Node(); node41.setNode_id(41);
        Node node40 = new Node(); node40.setNode_id(40);
        desiredPath = new ArrayList<>();
        desiredPath.add(node54); desiredPath.add(node46); desiredPath.add(node45);
        desiredPath.add(node42); desiredPath.add(node41); desiredPath.add(node40);
        assertEquals("Path is not optimal for pathfinder search 1.", desiredPath, returnedPath);

        //make sure distance is 115 ft
        assertEquals("Distance is not correct for pathfinder search 1.", 115, pf.getTotalDistance());


        //TEST SEARCH 2

        pf = new Pathfinder(context);
        returnedPath = pf.search(19, 37);

        //make sure path is the right path: 19, 20, 21, 23, 36, 37
        Node node19 = new Node(); node19.setNode_id(19);
        Node node20 = new Node(); node20.setNode_id(20);
        Node node21 = new Node(); node21.setNode_id(21);
        Node node23 = new Node(); node23.setNode_id(23);
        Node node36 = new Node(); node36.setNode_id(36);
        Node node37 = new Node(); node37.setNode_id(37);
        desiredPath = new ArrayList<>();
        desiredPath.add(node19); desiredPath.add(node20); desiredPath.add(node21);
        desiredPath.add(node23); desiredPath.add(node36); desiredPath.add(node37);
        assertEquals("Path is not optimal for pathfinder search 2.", desiredPath, returnedPath);

        //make sure distance is 83 ft
        assertEquals("Distance is not correct for pathfinder search 2.", 83, pf.getTotalDistance());


        //TEST SEARCH 3
        //THIS ONE CURRENTLY DOES NOT PASS BECAUSE OF SOME MISSING ADJACENCIES.

        pf = new Pathfinder(context);
        returnedPath = pf.search(162, 55);

        //make sure path is the right path: 162, 163, 164, 55
        Node node162 = new Node(); node162.setNode_id(162);
        Node node163 = new Node(); node163.setNode_id(163);
        Node node164 = new Node(); node164.setNode_id(164);
        Node node55 = new Node(); node55.setNode_id(55);
        desiredPath = new ArrayList<>();
        desiredPath.add(node162); desiredPath.add(node163);
        desiredPath.add(node164); desiredPath.add(node55);
        assertEquals("Path is not optimal for pathfinder search 3.", desiredPath, returnedPath);

        //make sure distance is roughly 86 ft
        assertEquals("Distance is not correct for pathfinder search 3.", 86, pf.getTotalDistance());
    }
}