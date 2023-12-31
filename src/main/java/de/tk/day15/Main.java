package de.tk.day15;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

enum OPERATION_CHARAKTER {
    DASH, EQUAL;
}

class Operation {
    String label;
    OPERATION_CHARAKTER operation;
    int focalLength = 0;
}

public class Main {

    static HashMap<Integer, LinkedList<Operation>> boxes = new HashMap<>();

    public static void main(String[] args) {
        String[] parsedSteps = parseSteps(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day15\\input.txt"));

        // Solution Task 1
        // int sum = 0;
        // System.out.println(holidayAsciiHelper("cm"));
        // for(String step : parsedSteps) {

        //     int ascii = holidayAsciiHelper(step);
        //     sum += ascii;
        //     System.out.println(step + " " + ascii);
        // }
        // System.out.println(sum);

        // Solution Task 2
        for (String step : parsedSteps) {
            Operation op = getOperation(step);
            // execute operation
            executeOperation(op);
            
        }
        // sum up Operation
        int sum = 0;
        for(int i = 0; i < 256; i++) {
            if(boxes.containsKey(i)) {
                LinkedList<Operation> box = boxes.get(i);
                for(int o = 0; o < box.size(); o++){
                    Operation op1 = box.get(o);
                    sum += (i + 1) * (o + 1) * op1.focalLength;
                }                
            }
        }
        System.out.println(sum);
    }

    private static void executeOperation(Operation op) {
        int box = Integer.valueOf(holidayAsciiHelper(op.label));
        if(op.operation == OPERATION_CHARAKTER.DASH) {
            if(boxes.containsKey(box)) {
                boxes.get(box).removeIf(o -> o.label.equals(op.label));
            }
        } else {
            if(boxes.containsKey(box)) {
                LinkedList<Operation> list = boxes.get(box);
                boolean found = false;
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).label.equals(op.label)) {
                        found = true;
                        Operation op1 = list.get(i);
                        op1.focalLength = op.focalLength;
                        list.set(i, op1);
                    }
                }
                if(!found) {
                    list.add(op);
                }
                boxes.replace(box, list);
            } else {
                LinkedList<Operation> list = new LinkedList<>();
                list.add(op);
                boxes.put(box, list);
            }
        }
    }

    private static Operation getOperation(String step) {
        Operation operation = new Operation();

        if(step.contains("-")) {
            operation.label = step.replace("-", "");
            operation.operation = OPERATION_CHARAKTER.DASH;
        } else {
            String[] split = step.split("=");
            operation.label = split[0];
            operation.focalLength = Integer.valueOf(split[1]);
            operation.operation = OPERATION_CHARAKTER.EQUAL;
        }
        return operation;
    }

    private static String[] parseSteps(String path) {
        BufferedReader reader;
        String[] ret = null; 
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            ret = line.split(",");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    private static int holidayAsciiHelper(String input) {
        int hashValue = 0;
        for(char c : input.toCharArray()) {
            hashValue += (int) c;
            hashValue *= 17;
            hashValue %= 256;
        }
        return hashValue;
    }
    
}
