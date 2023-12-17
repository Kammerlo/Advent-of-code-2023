package de.tk.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.javatuples.Triplet;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import nu.pattern.OpenCV;


class Map {
    String[][] map; 
    int xStart;
    int yStart;
}

enum Direction {
    UP,DOWN,LEFT,RIGHT;
}

public class Main {

    

    public static void main(String[] args) {
        OpenCV.loadLocally();
        MatOfPoint2f contour = new MatOfPoint2f();
        
        Map testMap1 = getMap(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day10\\input.txt"));
        
        int loopLength1 = getLoopLengthForMap(testMap1, contour);
        System.out.println("Solution Task1: " + loopLength1 / 2);

        int pointsWithinContour = 0;
        for(int x = 0; x < testMap1.map.length; x ++) {
            for(int y = 0; y < testMap1.map.length; y++) {
                if(Imgproc.pointPolygonTest(contour, new Point(x +1 , y +1 ), false) > 0.0d) {
                    pointsWithinContour++;
                }
            }
        }
        System.out.println("Solution Task2: " + pointsWithinContour);
    }

    private static int getLoopLengthForMap(Map map, MatOfPoint2f contour) {
        
        List<Triplet<Integer,Integer, Direction>> startPositions = getStartDirections(map);
        int length1 = getLoopLengthForStartPair(startPositions.get(0), map, contour);

        
        return length1;
    }

    private static int getLoopLengthForStartPair(Triplet<Integer, Integer, Direction> pair, Map mapObj, MatOfPoint2f contour) {
        String[][] map = mapObj.map;
        int x = pair.getValue0();
        int y = pair.getValue1();
        String currentTile = map[x][y];
        Direction currentDirection = pair.getValue2();
        int currentSteps = 1; // 1 since we already did one step
        List<Point> points = new ArrayList<>();
        points.add(new Point(mapObj.xStart,mapObj.yStart)); // since we aren't starting at S
        while(!currentTile.equals("S")) {
            currentSteps++; 
            points.add(new Point(x,y));
            if(currentTile.equals("|")) {
                if(currentDirection != Direction.DOWN) {
                    y = y - 1;
                    currentDirection = Direction.UP;
                } else {
                    y = y + 1;
                currentDirection = Direction.DOWN;
                } 
            } else if(currentTile.equals("-") ) {
                if(currentDirection != Direction.LEFT) {
                    x = x + 1;
                    currentDirection = Direction.RIGHT;
                } else {
                    x = x - 1;
                    currentDirection = Direction.LEFT;
                }
                
            } else if(currentTile.equals("L")) {
                if(currentDirection == Direction.LEFT) {
                    y = y - 1;
                    currentDirection = Direction.UP;
                } else {
                    x = x + 1;
                    currentDirection = Direction.RIGHT;
                }
            } else if(currentTile.equals("J")) {
                if(currentDirection == Direction.RIGHT) {
                    y = y - 1;
                    currentDirection = Direction.UP;
                } else {
                    x = x - 1;
                    currentDirection = Direction.LEFT;
                }
            } else if(currentTile.equals("7")) {
                if(currentDirection == Direction.RIGHT) {
                    y = y + 1;
                    currentDirection = Direction.DOWN;
                } else {
                    x = x - 1;
                    currentDirection = Direction.LEFT;
                }
            } else if(currentTile.equals("F")) {
                if(currentDirection == Direction.LEFT) {
                    y = y + 1;
                    currentDirection = Direction.DOWN;
                } else {
                    x = x + 1;
                    currentDirection = Direction.RIGHT;
                }
            }
            currentTile = map[x][y];
        }
        contour.fromList(points);
        
        return currentSteps;
    }

    private static List<Triplet<Integer,Integer,Direction>> getStartDirections(Map map) {
        List<Triplet<Integer,Integer,Direction>> startPositions = new ArrayList<>();
        // look above
        if(map.yStart > 0) {
            String above = map.map[map.xStart][map.yStart - 1];
            if(above.equals("|") || above.equals("7") || above.equals("F")) {
                startPositions.add(new Triplet<Integer,Integer,Direction>(map.xStart, map.yStart - 1, Direction.UP));
            }
        }
        // look down
        if(map.yStart < map.map.length) {
            String down = map.map[map.xStart][map.yStart + 1];
            if(down.equals("|") || down.equals("L") || down.equals("J")) {
                startPositions.add(new Triplet<Integer,Integer,Direction>(map.xStart , map.yStart + 1, Direction.DOWN));
            }
        }
        // look right 
        if(map.xStart < map.map.length) {
            String right = map.map[map.xStart + 1][map.yStart];
            if(right.equals("-") || right.equals("7") || right.equals("J")) {
                startPositions.add(new Triplet<Integer,Integer,Direction>(map.xStart + 1, map.yStart, Direction.RIGHT));
            }
        }
        // look left
        if(map.xStart > 0) {
            String left = map.map[map.xStart - 1][map.yStart];
            if(left.equals("-") || left.equals("F") || left.equals("L")) {
                startPositions.add(new Triplet<Integer,Integer,Direction>(map.xStart - 1, map.yStart, Direction.LEFT));
            }
        }
        return startPositions;
    }

    private static Map getMap(String path) {
        BufferedReader reader;
        Map m = new Map();
        List<String[]> stringMap = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            // First we read the whole document to ensure X,Y Coordinates in this order
            List<String> inputDocument = new ArrayList<>();
            String line = reader.readLine();
            while(line != null) {
                inputDocument.add(line);
                line = reader.readLine();
            }
            reader.close();

            // construct map
            String[][] map = new String[inputDocument.get(0).length()][inputDocument.size()];
            for(int x = 0; x < inputDocument.get(0).length(); x++) {
                for(int y = 0; y < inputDocument.size();y++) {
                    String tile = inputDocument.get(y).substring(x, x + 1);
                    map[x][y] = tile;
                    if(tile.equals("S")) {
                        m.xStart = x;
                        m.yStart = y;
                    }
                }
            }
            m.map = map;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return m;
    }
    
}
