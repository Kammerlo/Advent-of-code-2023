package de.tk.day8;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Node {
    String left;
    String right;
    boolean visited = false;
}

class SearchResult {
    int count;
    boolean successfull;

    SearchResult(SearchResult s) {
        this.count = s.count;
        this.successfull = s.successfull;
    }
    SearchResult(){

    }
}

public class Main {

    static HashMap<String, Node> map = new HashMap();
    static List<String> startingNodes = new ArrayList<>();
    static String instructions = "LRRLRLRRLRRRLRLRLRRLRRRLRRRLRRLRRRLRLRLRLRLRLRLRRRLRRLRRRLLLLRRRLRLLLRRRLLRLLRRRLRRRLRLRRLRRRLRRRLLRRRLRLRRRLLRRRLRLLRRRLRRLLRLRLRLRRRLRLLRLRLRRRLRLLRLRLRRRLLRRRLRRLRRRLRLRRLRLRRLRLRRLRRRLLRRRLLLRRRLLRRLRRLRRLRLLRRLRRRLRRLRLRLRRLRRLLLRRLRLRRRLRRRLRRRLLLRLRRRLLRRRLRLLRRRR";

    public static void main(String[] args) {
        fillMap(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day8\\input.txt"));

        Node startNode = map.get("AAA");
        SearchResult start = new SearchResult();
        start.count = 0;
        start.successfull = false;
        // SearchResult s = searchForZ(startNode, start);
        // System.out.println(s.count);

        int followTheInstructions = followTheInstructions();
        System.out.println("Single Way out:" + followTheInstructions);

        long asAGhost = followTheInstructionsAsAGhost2();
        System.out.println("As a ghost: " + asAGhost);
    }

    /**
     * Brute force approach
     * @return
     */
    private static long followTheInstructionsAsAGhost() {
        String[] currentNodes = new String[startingNodes.size()];
        startingNodes.toArray(currentNodes);
        long count = 0;
        boolean found = false;
        char[] charArray = instructions.toCharArray();
        int arrayCounter = 0;
        while(!found) {
            arrayCounter = arrayCounter % instructions.length();
            char direction = charArray[arrayCounter];
            // Updating all Nodes
            for(int i = 0; i < currentNodes.length;i++) {
                if(direction == 'L')
                    currentNodes[i] = map.get(currentNodes[i]).left;
                else 
                    currentNodes[i] = map.get(currentNodes[i]).right;
            }
            count++;
            arrayCounter++;
            found = checkIfEveryoneOnZ(currentNodes);
            System.out.println("Current Step: " + count);
        }
        return count;
    }

    /**
     * Solution after I got inspired by other colleagues :) 
     * @return
     */
    private static long followTheInstructionsAsAGhost2() {
        String[] currentNodes = new String[startingNodes.size()];
        startingNodes.toArray(currentNodes);
        char[] charArray = instructions.toCharArray();
        List<Integer> cycles = new ArrayList<>();
        for(String s : currentNodes) {
            int nodeCounter = 0;
            int instructionCounter = 0;
            while(!s.endsWith("Z")) {
                instructionCounter = nodeCounter % instructions.length();
                char direction = charArray[instructionCounter];
                if(direction == 'L')
                    s = map.get(s).left;
                else
                    s = map.get(s).right;
                nodeCounter++;
            }
            cycles.add(nodeCounter);
        }
        for(Integer c : cycles) {
            System.out.println(c);
        }
        int[] cyclesArray = cycles.stream().mapToInt(i->i).toArray();

        return lcm_of_array_elements(cyclesArray);
    }

    // found code at https://www.geeksforgeeks.org/lcm-of-given-array-elements/
    private static long lcm_of_array_elements(int[] element_array)
    {
        long lcm_of_array_elements = 1;
        int divisor = 2;
         
        while (true) {
            int counter = 0;
            boolean divisible = false;
             
            for (int i = 0; i < element_array.length; i++) {
 
                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.
 
                if (element_array[i] == 0) {
                    return 0;
                }
                else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }
 
                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }
 
            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }
 
            // Check if all element_array is 1 indicate 
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }


    private static boolean checkIfEveryoneOnZ(String[] currentNodes) {
        boolean reachedZ = true;
        for(String s : currentNodes) {
            if(!s.endsWith("Z")) {
                reachedZ = false;
                break;
            }
        }
        return reachedZ;
    }

    private static int followTheInstructions() {
        char[] charArray = instructions.toCharArray();
        String currentNode = "AAA";
        int count = 0;
        while(!currentNode.equals("ZZZ")) {
            char direction = charArray[count % instructions.length()];
            if(direction == 'L')
                currentNode = map.get(currentNode).left;
            else 
                currentNode = map.get(currentNode).right;
            count++;
        }
        return count;
    }

    private static SearchResult searchForZ(Node n, SearchResult s) {
        if(n.visited) {
            s.successfull = false;
            return s;
        }
        n.visited = true;
        s.count++;
        if(n.left.equals("ZZZ") || n.right.equals("ZZZ")) {
            s.successfull = true;
            s.count++; // we need to do this one step
            return s;
        } else {
            Node left = map.get(n.left);
            Node right = map.get(n.right);
            SearchResult leftSR = searchForZ(left, new SearchResult(s));
            SearchResult rightSR = searchForZ(right, new SearchResult(s));
            if(leftSR.successfull) 
                return leftSR;
            if(rightSR.successfull)
                return rightSR;
            s.count = leftSR.count < rightSR.count ? leftSR.count : rightSR.count;
            return s;
        }
    }

    private static void fillMap(String path) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null) {
                String name = line.substring(0, 3);
                String left = line.substring(7, 10);
                String right = line.substring(12, 15);

                if(name.endsWith("A"))
                    startingNodes.add(name);

                Node n = new Node();
                n.left = left;
                n.right = right;
                map.put(name, n);

                line = reader.readLine();
            }
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
