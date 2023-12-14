package de.tk.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import nu.pattern.OpenCV;

public class Main {
    private static List<Pair<Integer,Integer>> galaxies = new ArrayList<>();

    public static void main(String[] args) {
        OpenCV.loadLocally();
        // SolutionPart1();
        // Solution task 2
        String input = "src\\main\\java\\de\\tk\\day11\\input.txt";
        List<Integer> expansionX = new ArrayList<>();
        List<Integer> expansionY = new ArrayList<>();

        List<Point> galaxys = fillExpansionLists(input, expansionX, expansionY);
        galaxys = applyExpansion(galaxys, expansionX, expansionY, 1000001);

        long sum = 0;
        int loopCounter = 0;
        while(loopCounter < galaxys.size() - 1){
            Point p1 = galaxys.get(loopCounter);
            for(int i = loopCounter + 1; i < galaxys.size();i++) {
                Point p2 = galaxys.get(i);
                sum += Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
            }
            loopCounter++;
        }
        System.out.println(sum);
        System.out.println("test");
    }

    private static List<Point> applyExpansion(List<Point> galaxys, List<Integer> expansionX, List<Integer> expansionY, int factor) {
        List<Point> enlargedGalaxy = new ArrayList<>();
        for(Point galaxy : galaxys) {
            int increases = 0;
            for(int x : expansionX) {
                if(x < galaxy.x) {
                    increases++;
                }
            }
            galaxy.x = galaxy.x + (factor - 1) * increases;
            increases = 0;
            for(int y : expansionY) {
                if(y < galaxy.y) {
                    increases++;
                }
            }
            galaxy.y = galaxy.y + (factor - 1) * increases;
            enlargedGalaxy.add(galaxy);
        }
        return enlargedGalaxy;
    }



    private static List<Point> fillExpansionLists(String path, List<Integer> expansionX, List<Integer> expansionY) {
        BufferedReader reader;
        List<List<Integer>> galaxyMap = new ArrayList<>();
        List<Point> galaxys = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            // First we read the whole document to ensure X,Y Coordinates in this order
            String line = reader.readLine();
            int yCounter = 0;
            while(line != null) {
                boolean isEmpty = true;
                char[] charArray = line.toCharArray();
                List<Integer> mapLine = new ArrayList<>();
                for(int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    if(c == '.') {
                        mapLine.add(0);
                    } else {
                        mapLine.add(1);
                        galaxys.add(new Point(i, yCounter));
                        isEmpty = false;
                    }
                }
                galaxyMap.add(mapLine);
                if(isEmpty) {
                    expansionY.add(yCounter);
                }
                yCounter++;
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        // adding y space expansion
        for(int x = 0; x < galaxyMap.get(0).size(); x++) {
            boolean isEmpty = true;
            for(int y = 0; y < galaxyMap.size(); y++) {
                if(galaxyMap.get(y).get(x) == 1) {
                    isEmpty = false;
                    break;
                }
            }
            if(isEmpty) {
                expansionX.add(x);
            }
        }
        return galaxys;
    }

    private static void SolutionPart1() {
        List<List<Integer>> galaxy = getGalaxyFromInput("src\\main\\java\\de\\tk\\day11\\input.txt");
        List<Point> galaxyPoints = getGalaxyPoints(galaxy);
        
        int sum = 0;
        int loopCounter = 0;
        while(loopCounter < galaxyPoints.size() - 1){
            Point p1 = galaxyPoints.get(loopCounter);
            for(int i = loopCounter + 1; i < galaxyPoints.size();i++) {
                Point p2 = galaxyPoints.get(i);
                sum += Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
            }
            loopCounter++;
        }
        System.out.println(sum);
        System.out.println("test");
    }

    private static List<Point> getGalaxyPoints(List<List<Integer>> galaxy) {
        List<Point> points = new ArrayList<>();
        for(int y = 0; y < galaxy.size(); y++) {
            for(int x = 0; x < galaxy.get(0).size(); x++) {
                if(galaxy.get(y).get(x)== 1) {
                    points.add(new Point(x,y));
                }
            }
        }
        return points;
    }

    private static List<List<Integer>> getGalaxyFromInput(String path) {
        BufferedReader reader;
        List<List<Integer>> galaxyMap = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            // First we read the whole document to ensure X,Y Coordinates in this order
            String line = reader.readLine();
            int yCounter = 0;
            while(line != null) {
                boolean isEmpty = true;
                char[] charArray = line.toCharArray();
                List<Integer> mapLine = new ArrayList<>();
                for(int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    if(c == '.') {
                        mapLine.add(0);
                    } else {
                        mapLine.add(1);
                        isEmpty = false;
                    }
                }
                galaxyMap.add(mapLine);
                if(isEmpty) {
                    galaxyMap.add(new ArrayList<>(mapLine)); // space expansion for y
                }
                yCounter++;
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        // adding y space expansion
        int galaxyXSize = galaxyMap.get(0).size();
        int galaxyYSize = galaxyMap.size();
        List<Integer> copyColumns = new ArrayList<>();
        for(int x = 0; x < galaxyXSize; x++) {
            boolean isEmpty = true;
            for(int y = 0; y < galaxyYSize; y++) {
                if(galaxyMap.get(y).get(x) == 1) {
                    isEmpty = false;
                    break;
                }
            }
            if(isEmpty) {
                copyColumns.add(x);
            }
        }
        for(int y = 0; y < galaxyMap.size(); y++) {
            for(int i = 0; i < copyColumns.size();i++) {
                galaxyMap.get(y).add(copyColumns.get(i) + i, 0);
            }
        }


        return galaxyMap;
    }
}
