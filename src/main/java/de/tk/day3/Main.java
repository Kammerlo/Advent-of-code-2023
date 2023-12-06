package de.tk.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

class PartNumbers {
    int number;
    int startPosition;
    int endPosition;
}

public class Main {

    // picks must include 12 red, 13 green and 14 blue
    public static void main(String[] args) {
        // task1();
        task2();
    }

    private static void task2() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day3\\input1.txt"));
            int sum = 0;
            String previousLin = "";
            String currentLine = ""; // no we 
            String nextLineeee = reader.readLine();

            Pattern pattern = Pattern.compile("\\*");

            while(nextLineeee != null) {
                previousLin = currentLine;
                currentLine = nextLineeee;
                nextLineeee = reader.readLine();

                Matcher matcher = pattern.matcher(currentLine);
                while(matcher.find()) {
                    boolean valid = false;
                    int gearRatio = 0;
                    List<String> ratios = new ArrayList<>();
                    int index = matcher.start() == 0 ? 0 : matcher.start();
                    // int endIndex = matcher.end(); // we don't need the end index, since we are only looking for a single character (*) 
                    String found = matcher.group();

                    if(!valid && Character.isDigit(currentLine.charAt(index - 1))) {
                        String numberString = String.valueOf(currentLine.charAt(index - 1));
                        int i = 2;
                        while(index - i >=0 && Character.isDigit(currentLine.charAt(index - i))) {
                            numberString = String.valueOf(currentLine.charAt(index - i)) + numberString;
                            i++;
                        }
                        if(gearRatio > 0) {
                                valid = true;
                            }
                            gearRatio = addToGearRatio(gearRatio, numberString);
                            ratios.add(numberString);
                    }
                    if(!valid && Character.isDigit(currentLine.charAt(index + 1))) {
                        String numberString = String.valueOf(currentLine.charAt(index + 1));
                        int i = 2;
                        while(index + i < currentLine.length() && Character.isDigit(currentLine.charAt(index + i))){
                            numberString = numberString + String.valueOf(currentLine.charAt(index + i));
                            i++;
                        }
                        if(gearRatio > 0) {
                                valid = true;
                            }
                            gearRatio = addToGearRatio(gearRatio, numberString);
                            ratios.add(numberString);
                    }

                    // checking previous line. In first line previousLin var is empty
                    if(!valid && !previousLin.isEmpty()) {
                        boolean isNumberAbove = isGear(previousLin,index - 1, index + 2);
                        if(isNumberAbove) {
                            
                            String substr = previousLin.substring(index - 1, index + 2);
                            String numberString = getNumberAboveGear(previousLin, index, substr);
                            if(gearRatio > 0 && valid == true) {
                                System.err.println("three numbers");
                            }
                            if(gearRatio > 0) {
                                valid = true;
                            }
                            
                            gearRatio = addToGearRatio(gearRatio, numberString);
                            ratios.add(numberString);
                        }
                    }

                    // next lime the same as previous
                    if(!valid  && nextLineeee != null) {
                        boolean isNumberBelow = isGear(nextLineeee, index - 1, index + 2);
                        if(isNumberBelow) {
                            
                            String substr = nextLineeee.substring(index - 1, index + 2);
                            String numberString = getNumberAboveGear(nextLineeee, index, substr);
                            if(gearRatio > 0 && valid == true) {
                                System.err.println("three numbers");
                            }
                            if(gearRatio > 0) {
                                valid = true;
                            }
                            gearRatio = addToGearRatio(gearRatio, numberString);
                            ratios.add(numberString);
                        }
                    }
                    // summing up
                    if(valid) {
                        
                        sum += Integer.valueOf(gearRatio);
                        System.out.println(sum);
                    }
                }

            }
            reader.close();

            System.out.println("Sum is: " + sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static String getNumberAboveGear(String previousLin, int index, String substr) {
        Pattern digitPattern = Pattern.compile("\\d+");
        Matcher matcher = digitPattern.matcher(substr);
        matcher.find();
        int digitIndex = matcher.start() + index - 1;
        int digitIndexBefore = digitIndex - 1;
        int digitIndexAfter = digitIndex + 1;
        String numberString = String.valueOf(previousLin.charAt(digitIndex));
        while(digitIndexBefore >= 0 && Character.isDigit(previousLin.charAt(digitIndexBefore))) {
            numberString = String.valueOf(previousLin.charAt(digitIndexBefore)) + numberString;
            digitIndexBefore--;
        }
        while(digitIndexAfter < previousLin.length() && Character.isDigit(previousLin.charAt(digitIndexAfter)) ) {
            numberString = numberString + String.valueOf(previousLin.charAt(digitIndexAfter));
            digitIndexAfter++;
        }
        return numberString;
    }

    private static int addToGearRatio(int gearRatio, String numberStr) {
        if(gearRatio == 0) {
            gearRatio = Integer.valueOf(numberStr); // this is the first case switch, so 
            return gearRatio;
        } else {
            gearRatio = gearRatio * Integer.valueOf(numberStr);
            return gearRatio;
        }
    }

    private static boolean isGear(String line, int i, int j) {
        boolean valid = false;
        String substr = line.substring(i, j);
        for(char c : substr.toCharArray()) {
            if(Character.isDigit(c)) {
                valid = true;
            }
        }
        return valid;
    }

    private static void task1() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day3\\input1.txt"));
            int sum = 0;
            String previousLin = "";
            String currentLine = "";
            String nextLineeee = reader.readLine();

            Pattern pattern = Pattern.compile("\\d{1,}");

            while(nextLineeee != null) {
                previousLin = currentLine;
                currentLine = nextLineeee;
                nextLineeee = reader.readLine();

                Matcher matcher = pattern.matcher(currentLine);
                while(matcher.find()) {
                    boolean valid = false;
                    int startIndex = matcher.start() == 0 ? 0 : matcher.start() - 1;
                    int endIndex = matcher.end(); // to get the exact position of the last included character and not the index after
                    String found = matcher.group();
                    // checking if it is a valid partnumber 
                    // checking currentline 
                    if(startIndex != 0 && currentLine.charAt(startIndex) != '.') {
                        valid = true;
                    } else if(endIndex != currentLine.length() && currentLine.charAt(endIndex) != '.') {
                        valid = true;
                    }
                    // checking previous line. In first line previousLin var is empty
                    if(!valid && !previousLin.isEmpty()) {
                        valid = checkLine(previousLin, startIndex, endIndex);
                    }

                    // next lime the same as previous
                    if(!valid  && nextLineeee != null) {
                        valid = checkLine(nextLineeee, startIndex, endIndex);
                    }
                    // summing up
                    if(valid) {
                        System.out.println(found);
                        sum += Integer.valueOf(found);
                    }
                }

            }
            reader.close();

            System.out.println("Sum is: " + sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkLine(String line, int startIndex, int endIndex) {
        
        boolean valid = false;
        try{
        String subString = line.substring(startIndex, endIndex == line.length() ? endIndex : endIndex + 1);
        subString = subString.replaceAll("\\.", "");
        if(subString.length() > 0) {
            valid = true;
        }
        } catch(Exception e ) {
            System.out.println(line);
            System.out.println("Something went wrong");
        }
        return valid;
    }
}