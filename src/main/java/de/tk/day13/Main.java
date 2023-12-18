package de.tk.day13;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String[][]> grids = readGrids(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day13\\input.txt"));


        // Solution Task 1;
        int sum = 0;
        for(String[][] grid : grids) {
            int xMirror = findXMirror(grid);
            int yMirror = findYMirror(grid);
            if(xMirror >= 0)
                sum += xMirror;
            if(yMirror >= 0)
                sum += 100 * yMirror;
        }
        System.out.println("Solution Task 1: " + sum);

        // Solution Task 2;
        int sumTask2 = 0;
        for(String[][] grid : grids) {
            int xMirror = findXSmudgeMirror(grid);
            int yMirror = findYSmudgeMirror(grid);
            if(xMirror >= 0)
                sumTask2 += xMirror;
            if(yMirror >= 0)
                sumTask2 += 100 * yMirror;
        }
        System.out.println("Solution Task 2: " + sumTask2);
    }

    private static int findXSmudgeMirror(String[][] strings) {
        for(int mirrorPosition = 1; mirrorPosition < strings[0].length; mirrorPosition++) {
            int errors = 0;
            for(int x1 = mirrorPosition - 1, x2 = mirrorPosition; x1 >= 0 && x2 < strings[0].length; x1--, x2++) {
                for(int y = 0; y < strings.length; y++) {
                    if(!strings[y][x1].equals(strings[y][x2])) {
                        errors = errors + 1;
                    }
                }
            }
            if(errors == 1) {
                return mirrorPosition;
            }
        }
        return -1;
    }

    private static int findYSmudgeMirror(String[][] strings) {
        for(int mirrorPosition = 1; mirrorPosition < strings.length; mirrorPosition++) {
            int errors = 0;
            for(int y1 = mirrorPosition - 1, y2 = mirrorPosition; y1 >= 0 && y2 < strings.length; y1--, y2++) {
                for(int x = 0; x < strings[0].length; x++) {
                    if(!strings[y1][x].equals(strings[y2][x])) {
                        errors = errors + 1;
                    }
                }
            }
            if(errors == 1) {
                return mirrorPosition;
            }
        }
        return -1;
    }

    private static int findYMirror(String[][] strings) {
        for(int mirrorPosition = 1; mirrorPosition < strings.length; mirrorPosition++) {
            boolean mirror = true;
            for(int y1 = mirrorPosition - 1, y2 = mirrorPosition; y1 >= 0 && y2 < strings.length; y1--, y2++) {
                for(int x = 0; x < strings[0].length; x++) {
                    if(!strings[y1][x].equals(strings[y2][x])) {
                        mirror = false;
                        break;
                    }
                }
                if(!mirror)
                    break;
            }
            if(mirror) {
                return mirrorPosition;
            }
        }
        return -1;
    }

    private static int findXMirror(String[][] strings) {
        for(int mirrorPosition = 1; mirrorPosition < strings[0].length; mirrorPosition++) {
            boolean mirror = true;
            for(int x1 = mirrorPosition - 1, x2 = mirrorPosition; x1 >= 0 && x2 < strings[0].length; x1--, x2++) {
                for(int y = 0; y < strings.length; y++) {
                    if(!strings[y][x1].equals(strings[y][x2])) {
                        mirror = false;
                        break;
                    }
                }
                if(!mirror)
                    break;
            }
            if(mirror) {
                return mirrorPosition;
            }
        }
        return -1;
    }

    private static List<String[][]> readGrids(String path) {
        BufferedReader reader;
        List<String[][]> grids = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            List<String[]> grid = new ArrayList<>();
            while (line != null) {
                if(line.isEmpty()) {
                    String[][] a = new String[grid.size()][];
                    grid.toArray(a);
                    grids.add(a);
                    grid = new ArrayList<>();
                } else {
                    grid.add(line.split(""));
                }
                line = reader.readLine();
            }
            String[][] a = new String[grid.size()][];
            grid.toArray(a);
            grids.add(a);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return grids;
    }


}
