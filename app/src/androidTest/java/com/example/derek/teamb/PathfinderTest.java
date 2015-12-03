package com.example.derek.teamb;

import android.test.AndroidTestCase;
import android.content.Context;
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
        List<Long> returnedPath, desiredPath;

        //TODO: Find actual distances of paths and verify path node id's

        //TEST SEARCH 1

        returnedPath = pf.search(54, 40);

        //make sure path is the right path: 54, 46, 45, 42, 41, 40
        desiredPath = new ArrayList<>();
        desiredPath.add((long) (54)); desiredPath.add((long) (46)); desiredPath.add((long) (45));
        desiredPath.add((long) (42)); desiredPath.add((long) (41)); desiredPath.add((long) (40));
        assertEquals("Path is not optimal for pathfinder search 1.", desiredPath, returnedPath);

        //make sure distance is roughly 144 ft
        assertEquals("Distance is not correct for pathfinder search 1.", 144, pf.getTotalDistance());


        //TEST SEARCH 2

        pf = new Pathfinder(context);
        returnedPath = pf.search(19, 37);

        //make sure path is the right path: 19, 20, 21, 23, 36, 37
        desiredPath = new ArrayList<>();
        desiredPath.add((long) (19)); desiredPath.add((long) (20)); desiredPath.add((long) (21));
        desiredPath.add((long) (23)); desiredPath.add((long) (36)); desiredPath.add((long) (37));
        assertEquals("Path is not optimal for pathfinder search 2.", desiredPath, returnedPath);

        //make sure distance is roughly 78 ft
        assertEquals("Distance is not correct for pathfinder search 2.", 78, pf.getTotalDistance());


        //TEST SEARCH 3

        pf = new Pathfinder(context);
        returnedPath = pf.search(162, 55);

        //make sure path is the right path: 162, 163, 164, 55
        desiredPath = new ArrayList<>();
        desiredPath.add((long) (162)); desiredPath.add((long) (163));
        desiredPath.add((long) (164)); desiredPath.add((long) (55));
        assertEquals("Path is not optimal for pathfinder search 3.", desiredPath, returnedPath);

        //make sure distance is roughly 86 ft
        assertEquals("Distance is not correct for pathfinder search 3.", 86, pf.getTotalDistance());
    }
}