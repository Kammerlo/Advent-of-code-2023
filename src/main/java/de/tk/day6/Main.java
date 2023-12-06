package de.tk.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Game {
    long time;
    long distance;
}

public class Main {

    static List<Game> games;
    static List<List<Long>> solutionsForGames = new ArrayList<>();
    
    public static void main(String[] args) {
        fillGameList();

        findSolutionForGames();
        int s = 0;
        for(List<Long> l : solutionsForGames) {
            if(s == 0) {
                s = l.size();
            } else {
                s = s * l.size();
            }
        }
        System.out.println(s);
    }

    private static void findSolutionForGames() {
        for(Game g : games) {
            List<Long> solutions = new ArrayList<>();
            for(Long i = 0l; i < g.time;i++) {
                long distance = i * (g.time - i);
                if(distance > g.distance) {
                    solutions.add(i);
                }
            }
            solutionsForGames.add(solutions);
        }
    }

    private static void fillGameList() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day6\\input2.txt"));
            String time = reader.readLine();
            time = time.replaceAll("[A-Z][a-z]{1,}:\\s{1,}", "");
            time = time.replaceAll("\\s{1,}", " "); // just to remove multiple whitespace with just one
            String[] times = time.split(" ");
            String distance = reader.readLine();
            distance = distance.replaceAll("[A-Z][a-z]{1,}:\\s{1,}", "");
            distance = distance.replaceAll("\\s{1,}", " "); // just to remove multiple whitespace with just one
            String[] distances = distance.split(" ");

            games = new ArrayList<>();
            for(int i = 0; i < times.length; i++) {
                Game g = new Game();
                
                g.time = Long.valueOf(times[i]);
                g.distance = Long.valueOf(distances[i]);
                games.add(g);
            }
            
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
