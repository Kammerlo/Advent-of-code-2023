package de.tk.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;

public class Main {

    public static void main(String[] args) {
        // getSolutionTask1();
        getSolutionTask2();
    }

    private static void getSolutionTask2() {
        List<int[]> testInputs = new ArrayList<>();
        fillMap("src\\main\\java\\de\\tk\\day9\\testInput.txt", testInputs);
        int testSum = 0;
        for(int[] line : testInputs) {
            testSum += calculatePreviousValue(line);
        }
        String testSolutionDebugString;
        if(testSum == 2) {
            testSolutionDebugString = "Test Worked!";
        } else {
            testSolutionDebugString = "Test solution is wrong";
        }
        System.err.println(testSolutionDebugString);
        System.out.println("TestSolution is expected to be: 2 Code result is: " + testSum);

        List<int[]> input1 = new ArrayList<>();
        fillMap("src\\main\\java\\de\\tk\\day9\\input.txt", input1);
        int solutionTask2 = 0;
        for(int[] line : input1) {
            solutionTask2 += calculatePreviousValue(line);
        }
        System.out.println("Solution for task 2 is: " + solutionTask2);
    }

    private static void getSolutionTask1() {
        List<int[]> testInputs = new ArrayList<>();
        fillMap("src\\main\\java\\de\\tk\\day9\\testInput.txt", testInputs);
        int testSum = 0;
        for(int[] line : testInputs) {
            testSum += calculateNextValue(line);
        }
        String testSolutionDebugString;
        if(testSum == 114) {
            testSolutionDebugString = "Test Worked!";
        } else {
            testSolutionDebugString = "Test solution is wrong";
        }
        System.err.println(testSolutionDebugString);
        System.out.println("TestSolution is expected to be: 114 Code result is: " + testSum);

        List<int[]> input1 = new ArrayList<>();
        fillMap("src\\main\\java\\de\\tk\\day9\\input.txt", input1);
        int solutionTask1 = 0;
        for(int[] line : input1) {
            solutionTask1 += calculateNextValue(line);
        }
        System.out.println("Solution for task 1 is: " + solutionTask1);
    }

    private static int calculatePreviousValue(int[] is) {
        if(isArrayOnlyZero(is)) {
            return 0;
        } else {
            int[] newLine = new int[is.length - 1];
            for(int i = 0; i < is.length - 1; i++)
            {
                newLine[i] = is[i + 1] - is[i];
            }
            int resultOfNewLine = calculatePreviousValue(newLine);
            int solution = is[0] - resultOfNewLine;
            return solution;
        }
    }

    private static int calculateNextValue(int[] is) {
        if(isArrayOnlyZero(is)) {
            return 0;
        } else {
            int[] newLine = new int[is.length - 1];
            for(int i = 0; i < is.length - 1; i++)
            {
                newLine[i] = is[i + 1] - is[i];
            }
            int resultOfNewLine = calculateNextValue(newLine);
            int solution = is[is.length - 1] + resultOfNewLine;
            return solution;
        }
    }

    private static boolean isArrayOnlyZero(int[] is) {
        boolean ret = true;
        for(int i : is) {
            if(i != 0) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    private static void fillMap(String path, List<int[]> inputs) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null) {
                String[] splitted = line.split(" ");
                int[] arr = new int[splitted.length];
                for(int i = 0; i < splitted.length; i++) {
                    arr[i] = Integer.valueOf(splitted[i]);
                }
                inputs.add(arr);

                line = reader.readLine();
            }
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
