package de.tk.day14;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Direction {
    NORTH, EAST, SOUTH, WEST;
}

public class Main {

    private static String[][] map;


    /**
     * DISCLAIMER!!!!
     * This code is super messy and lots of copy pastes, but for the advent of code it's fine.
     * I write it here because it is more messy than the other days :D 
     * @param args
     */
    public static void main(String[] args) {
        fillMap(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day14\\input.txt"));
        // Solution Task 1
        
        // printOutMap(map);
        // map = tiltMapNorth(map);
        // printOutMap(map);
        // int loadNorth = calculateWeightNorth();
        // System.out.println("Solution Task 1: " + loadNorth);

        // Solution Task 2 

        int rotations = 1000;   
        String[][] previosCycle = map.clone();
        for(int i = 0; i < rotations; i++) {
            System.out.print(i);
            map = cycleMap(map);
            // if(Arrays.deepEquals(map, previosCycle)) {
            //     System.out.print("Equal ");
            //     int loadNorth = calculateWeightNorth();
            //     System.out.print(loadNorth + "    ");
            // }
            int loadNorth = calculateWeightNorth();
            System.out.println("    Load North: " + loadNorth);
        }
        int loadNorth = calculateWeightNorth();
        System.out.println(loadNorth);
        printOutMap(map);

        /**
         * Solution for Task 2 is a bit tricky. So you can brute force it with my solution. But I found out that there is a cycle at 1000 steps so the solution for 1000 steps is same like 1000000000.
         * This saved me half an hour (or more) computation time :) 
         */
        
    }

    private static void printOutMap(String[][] map) {
        System.out.println("-------------");
        for(String[] arr : map) {
            for(String s : arr) {
                System.out.print(s);
            }
            System.out.println("");
        }
        System.out.println("-------------");
    }

    private static int calculateWeightNorth() {
        int loadNorth = 0;
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length;x++) {
                if(map[y][x].equals("O")) {
                    loadNorth += map.length - y;
                }
            }
        }
        return loadNorth;
    }

    private static String[][] cycleMap (String[][] map) {
        map = tiltMapNorth(map);
        // printOutMap(map);
        map = tiltMapWest(map);
        // printOutMap(map);
        map = tiltMapSouth(map);
        // printOutMap(map);
        map = tiltMapEast(map);
        // printOutMap(map);
        return map;
    }

    private static String[][] tiltMapNorth(String[][] map) {
        for(int y = 1; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x].equals("O")) {
                    map = moveRockNorth(map, y,x);
                }
            }
        }
        return map;
    }

    private static String[][] tiltMapEast(String[][] map) {
        for(int y = 0 ; y < map.length; y++) {
            for(int x = map[y].length - 1; x >= 0; x--) {
                if(map[y][x].equals("O")) {
                    map = moveRockEast(map, y,x);
                }
            }
        }
        return map;
    }

    private static String[][] tiltMapSouth(String[][] map) {
        for(int y = map.length - 1; y >= 0; y--) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x].equals("O")) {
                    map = moveRockSouth(map, y,x);
                }
            }
        }
        return map;
    }

    private static String[][] tiltMapWest(String[][] map) {
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x].equals("O")) {
                    map = moveRockWest(map, y,x);
                }
            }
        }
        return map;
    }

    private static String[][] moveRockNorth(String[][] map, int y, int x) {
        boolean moved = false;
        if(y == 0) {
            return map; // no movement at all
        }
        int yStart = y;
        String lookAt = "";
        do {
            yStart--;
            if(yStart < 0)
                break;
            lookAt = map[yStart][x];

            moved = true;
        } while(lookAt.equals("."));
        if(moved) {
            yStart++; // ++ since we run on a field which is occupied
            map[y][x] = ".";
            map[yStart][x] = "O";
        }
        return map;
    }

    private static String[][] moveRockSouth(String[][] map, int y, int x) {
        boolean moved = false;
        if(y == map.length - 1) {
            return map; // no movement at all
        }
        int yStart = y;
        String lookAt = "";
        do {
            yStart++;
            if(yStart == map.length)
                break;
            lookAt = map[yStart][x];

            moved = true;
        } while(lookAt.equals("."));
        if(moved) {
            yStart--; // -- since we run on a field which is occupied
            map[y][x] = ".";
            map[yStart][x] = "O";
        }
        return map;
    }

    private static String[][] moveRockWest(String[][] map, int y, int x) {
        boolean moved = false;
        if(x == 0) {
            return map; // no movement at all
        }
        int xStart = x;
        String lookAt = "";
        do {
            xStart--;
            if(xStart < 0)
                break;
            lookAt = map[y][xStart];

            moved = true;
        } while(lookAt.equals("."));
        if(moved) {
            xStart++; // -- since we run on a field which is occupied
            map[y][x] = ".";
            map[y][xStart] = "O";
        }
        return map;
    }

    private static String[][] moveRockEast(String[][] map, int y, int x) {
        boolean moved = false;
        if(x == map[y].length - 1) {
            return map; // no movement at all
        }
        int xStart = x;
        String lookAt = "";
        do {
            xStart++;
            if(xStart == map[y].length)
                break;
            lookAt = map[y][xStart];

            moved = true;
        } while(lookAt.equals("."));
        if(moved) {
            xStart--; // ++ since we run on a field which is occupied
            map[y][x] = ".";
            map[y][xStart] = "O";
        }
        return map;
    }
    

    private static void fillMap(String path) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            List<String[]> localMap = new ArrayList<>();
            while(line != null) {
                localMap.add(line.split(""));
                line = reader.readLine();
            }
            map = new String[localMap.size()][];
            localMap.toArray(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
