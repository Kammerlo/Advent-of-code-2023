package de.tk.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MapValues {
    long sourceStart;
    long destiationStart;
    long length;
}

public class Main {

    static List<Long> seeds = new ArrayList<>();
    static List<MapValues>[] mapValuesArray = new List[7];

    static List<HashMap<Long,Long>> matrix;
    
    public static void main(String[] args) {
        fillLists();

        // solution for Task 1
        
        // fillMatrix();
        // long minLoc = Long.MAX_VALUE;
        // for(long seed : seeds) {
        //     long loc = findSeedLocation(seed);
        //     minLoc = Math.min(minLoc, loc);
        // }

        // solution for task 2
        long minLoc = Long.MAX_VALUE;
        for(int i = 0; i < seeds.size();i+=2) {
            for(int j=0;j < seeds.get(i + 1);j++) {
                long loc = findSeedLocation(seeds.get(i) + j);
                minLoc = Math.min(minLoc, loc);
            }
        }
        System.out.println("Min Loc = " + minLoc);
    }

    private static long findSeedLocation(long seed) {
        long source = seed;
        long destination = 0l;
        for(List<MapValues> mapValues : mapValuesArray) {
            MapValues found = null;
            for(MapValues values : mapValues) {
                if(isSeedInValues(source,values)) {
                    found = values;
                    break;
                }
            }
            if(found == null) {
                destination = source;
            } else {
                destination = found.sourceStart + (source - found.destiationStart);
            }
            source = destination;
        }
        return destination;
    }


    private static boolean isSeedInValues(long seed, MapValues values) {
        if(values.destiationStart <= seed && seed < values.destiationStart + values.length) {
            return true;
        }
        return false;
    }

    private static void fillLists() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day5\\input.txt"));
            String line = reader.readLine();
            seeds = fillSeedList(line);
            line = reader.readLine(); // reading empty line
            for(int i = 0; i < mapValuesArray.length;i++) {
                mapValuesArray[i] = fillMapValues(reader);
            }

            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Long> fillSeedList(String line) {
            String seeds = line.replaceAll("seeds:\\s", "");
            String[] split = seeds.split(" ");
            List<Long> seedsList = new ArrayList<>();
            for(String s : split) {
                seedsList.add(Long.valueOf(s));
            }
            return seedsList;
    }

    private static List<MapValues> fillMapValues(BufferedReader reader) {
        String line;
        List<MapValues> mapValues = new ArrayList<>();
        try {
            line = reader.readLine(); // reading header line seed-to-soil map
            line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] split = line.split(" ");
                MapValues values = new MapValues();
                long sourceStart = Long.valueOf(split[0]);
                long destiationStart = Long.valueOf(split[1]);
                long length = Long.valueOf(split[2]);
                values.sourceStart = sourceStart;
                values.destiationStart = destiationStart;
                values.length = length;
                mapValues.add(values);
                //reading next line
                line = reader.readLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return mapValues;
    }

}
