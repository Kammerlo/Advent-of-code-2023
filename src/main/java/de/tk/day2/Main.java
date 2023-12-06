package de.tk.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class Main {

    // picks must include 12 red, 13 green and 14 blue
    public static void main(String[] args) {
        // task1();
        task2();
}

private static void task2() {
    BufferedReader reader;
    try {
        reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day2\\input1.txt"));
        String line = reader.readLine();
        int sum = 0;
        while(line != null) {
            // each line a game and it starts with Game XXX: 
            // first remove the prefix
            String game = line.replaceAll("Game\\s\\d{1,3}:\\s", "");
            game = StringUtils.deleteWhitespace(game); // remove whitespaces
            String[] picks = game.split(";"); // split for the picks

            int redNumber = 0;
            int greenNumber = 0;
            int blueNumber = 0;
            for(String pick : picks) {
                String[] colorCubes = pick.split(",");
                for(String cubes : colorCubes) {
                    if(cubes.contains("red")) {
                        cubes = cubes.replace("red", "");
                        int redPick = Integer.valueOf(cubes);
                        if(redPick > redNumber) {
                            redNumber = redPick;
                        }
                    }
                    if(cubes.contains("green")) {
                        cubes = cubes.replace("green", "");
                        int greenPick = Integer.valueOf(cubes);
                        if(greenPick > greenNumber) {
                            greenNumber = greenPick;
                        }
                    }
                    if(cubes.contains("blue")) {
                        cubes = cubes.replace("blue", "");
                        int bluePick = Integer.valueOf(cubes);
                        if(bluePick > blueNumber) {
                            blueNumber = bluePick;
                        }
                    }
                }
            }
            sum += (redNumber * greenNumber * blueNumber);

            line = reader.readLine();
        }
        reader.close();

        System.out.println("Sum is: " + sum);
    } catch(IOException e) {
        e.printStackTrace();
    }
}


private static void task1() {
    BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day2\\input1.txt"));
            String line = reader.readLine();
            int sum = 0;
            int gameCounter = 1;
            while(line != null) {
                // each line a game and it starts with Game XXX: 
                // first remove the prefix
                String game = line.replaceAll("Game\\s\\d{1,3}:\\s", "");
                game = StringUtils.deleteWhitespace(game); // remove whitespaces
                String[] picks = game.split(";"); // split for the picks

                boolean possibleGame = true;

                for(String pick : picks) {
                    String[] colorCubes = pick.split(",");
                    for(String cubes : colorCubes) {
                        if(cubes.contains("red")) {
                            cubes = cubes.replace("red", "");
                            if(Integer.valueOf(cubes) > 12) {
                                possibleGame = false;
                                break;
                            } 
                        }
                        if(cubes.contains("green")) {
                            cubes = cubes.replace("green", "");
                            if(Integer.valueOf(cubes) > 13) {
                                possibleGame = false;
                                break;
                            } 
                        }
                        if(cubes.contains("blue")) {
                            cubes = cubes.replace("blue", "");
                            if(Integer.valueOf(cubes) > 14) {
                                possibleGame = false;
                                break;
                            } 
                        }
                    }
                    if(!possibleGame) {
                        break;
                    }
                }
                if(possibleGame) {
                    sum += gameCounter;
                }

                gameCounter++;
                line = reader.readLine();
            }
            reader.close();

            System.out.println("Sum is: " + sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
