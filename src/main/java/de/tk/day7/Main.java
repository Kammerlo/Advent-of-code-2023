package de.tk.day7;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.Element;

class Hand {
    String cards;
    int bid;
}

enum HandResult {
    FIVE_OF_A_KIND(0),
    FOUR_OF_A_KIND(1),
    FULL_HOUSE(2),
    THREE_OF_A_KIND(3),
    TWO_PAIR(4),
    ONE_PAIR(5),
    HIGH_CARD(6);

    public final int strength;

    private HandResult(int strength){
        this.strength = strength;
    }
}

public class Main {
    private static final Character[] CARDS = {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
    private static final Character[] CARDS_JOKER = {'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'};
    private static final List<Character> CARDS_LIST = Arrays.asList(CARDS);
    private static final List<Character> CARDS_LIST_JOKER = Arrays.asList(CARDS_JOKER);
    private static List<Hand> hands = new ArrayList<>();
    private static Comparator<Hand> handComparatorWithoutJoker = new Comparator<Hand>() {
        
        @Override
        public int compare(Hand h1, Hand h2) { // if h1 > 1 if h2 > -1 if == 0 
            if(h1.cards.equals(h2.cards)) // just in case
                return 0; 
            HandResult handResult1 = getHandResultWithoutJoker(h1.cards);
            HandResult handResult2 = getHandResultWithoutJoker(h2.cards);
            
            if(handResult1 == handResult2) { // same value
                for(int i = 0; i < h1.cards.length(); i++) {
                    char c1 = h1.cards.charAt(i);
                    char c2 = h2.cards.charAt(i);
                    int compare = cardComparatorWithoutJoker.compare(c1, c2);
                    if(compare > 0) {
                        return 1;
                    } else if (compare < 0) {
                        return -1;
                    }
                }
            }
            if(handResult1.strength < handResult2.strength) {
                return 1;
            } else {
                return -1;
            }
        };
    };

    private static Comparator<Hand> handComparatorWithJoker = new Comparator<Hand>() {
        
        @Override
        public int compare(Hand h1, Hand h2) { // if h1 > 1 if h2 > -1 if == 0 
            if(h1.cards.equals(h2.cards)) // just in case
                return 0; 
            HandResult handResult1 = getHandResultWithJoker(h1.cards);
            HandResult handResult2 = getHandResultWithJoker(h2.cards);
            
            if(handResult1 == handResult2) { // same value
                for(int i = 0; i < h1.cards.length(); i++) {
                    char c1 = h1.cards.charAt(i);
                    char c2 = h2.cards.charAt(i);
                    int compare = cardComparatorWithJoker.compare(c1, c2);
                    if(compare > 0) {
                        return 1;
                    } else if (compare < 0) {
                        return -1;
                    }
                }
            }
            if(handResult1.strength < handResult2.strength) {
                return 1;
            } else {
                return -1;
            }
        };
    };

    private static Comparator<Character> cardComparatorWithoutJoker = new Comparator<Character>() {
        @Override
        public int compare(Character o1, Character o2) {
            if(o1.equals(o2)) {
                return 0;
            }
            int i1 = CARDS_LIST.indexOf(o1);
            int i2 = CARDS_LIST.indexOf(o2);
            if(i1 < i2) {
                return 1;
            } else {
                return -1;
            }
        };
    };

    private static Comparator<Character> cardComparatorWithJoker = new Comparator<Character>() {
        @Override
        public int compare(Character o1, Character o2) {
            if(o1.equals(o2)) {
                return 0;
            }
            int i1 = CARDS_LIST_JOKER.indexOf(o1);
            int i2 = CARDS_LIST_JOKER.indexOf(o2);
            if(i1 < i2) {
                return 1;
            } else {
                return -1;
            }
        };
    };

    private static Comparator<Character> cardComparatorWith = new Comparator<Character>() {
        @Override
        public int compare(Character o1, Character o2) {
            if(o1.equals(o2)) {
                return 0;
            }
            int i1 = CARDS_LIST_JOKER.indexOf(o1);
            int i2 = CARDS_LIST_JOKER.indexOf(o2);
            if(i1 < i2) {
                return 1;
            } else {
                return -1;
            }
        };
    };
    
    public static void main(String[] args) {
        parseHands(FilenameUtils.separatorsToSystem("src\\main\\java\\de\\tk\\day7\\testInput.txt"));
        // Solution Task 1
        // hands.sort(handComparatorWithoutJoker);
        // List<Hand> reversed = hands;
        // long result = 0;
        // for(int i = 0; i < reversed.size(); i++) {
        //     HandResult handResult1 = getHandResultWithoutJoker(reversed.get(i).cards);
        //     System.out.println("Hand: " + reversed.get(i).cards + " Value: " + handResult1);
        //     int rank = i + 1;
        //     int bid = reversed.get(i).bid;
        //     result = result + (long)(bid * rank); 
        // }
        // System.out.println(result);


        // solution Task 2
        hands.sort(handComparatorWithJoker);
        List<Hand> reversed = hands;
        long result = 0;
        for(int i = 0; i < reversed.size(); i++) {
            HandResult handResult1 = getHandResultWithJoker(reversed.get(i).cards);
            System.out.println("Hand: " + reversed.get(i).cards + " Value: " + handResult1);
            int rank = i + 1;
            int bid = reversed.get(i).bid;
            result = result + (long)(bid * rank); 
        }
        System.out.println(result);
    }

    private static void parseHands(String s) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(s));
            String line = reader.readLine();
            while(line != null) {
                String[] split = line.split(" ");
                Hand h = new Hand();
                h.cards = split[0];
                h.bid = Integer.valueOf(split[1]);
                hands.add(h);


                line = reader.readLine();
            }
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static HandResult getHandResultWithJoker(String cards) {
        Matcher matcher = Pattern.compile("J{1}").matcher(cards);
        int jCount = 0;
        while(matcher.find()) {
            jCount++;
        }
        System.out.println("JString: " + cards + " Count " + jCount);
        String cardsWithoutJ = cards.replace("J", "");
        HandResult handResult = getHandResultWithoutJoker(cardsWithoutJ);
        HandResult jokerResult = handResult;
        if(handResult == HandResult.FOUR_OF_A_KIND && jCount == 1) {
            jokerResult = HandResult.FIVE_OF_A_KIND;
        } 
        if(handResult == HandResult.THREE_OF_A_KIND ) {
            if(jCount == 2)
                jokerResult = HandResult.FIVE_OF_A_KIND;
            if(jCount == 1) 
                jokerResult = HandResult.FOUR_OF_A_KIND;
        } 
        if(handResult == HandResult.TWO_PAIR && jCount == 1) {
            jokerResult = HandResult.FULL_HOUSE;
        } 
        if(handResult == HandResult.ONE_PAIR){
            if(jCount == 3)
                jokerResult = HandResult.FIVE_OF_A_KIND;
            if(jCount == 2)
                jokerResult = HandResult.FOUR_OF_A_KIND;
            if(jCount == 1)
                jokerResult = HandResult.THREE_OF_A_KIND;
        } 
        if(handResult == HandResult.HIGH_CARD) {
            if(jCount == 5)
                jokerResult = HandResult.FIVE_OF_A_KIND;
            if(jCount == 4)
                jokerResult = HandResult.FIVE_OF_A_KIND;
            if(jCount == 3)
                jokerResult = HandResult.FOUR_OF_A_KIND;
            if(jCount == 2)
                jokerResult = HandResult.THREE_OF_A_KIND;
            if(jCount == 1)
                jokerResult = HandResult.ONE_PAIR;
        }
        return jokerResult;
    }

    private static HandResult getHandResultWithoutJoker(String cards) {
        char[] charArray = cards.toCharArray();
        Arrays.sort(charArray);
        Arrays.sort(charArray);
        String sortedString = new String(charArray);
    

        if(!matchRegex(sortedString,"(.)\\1{4,}").isEmpty()) { // 5 matching characters
            return HandResult.FIVE_OF_A_KIND;
        }
        if (!matchRegex(sortedString,"(.)\\1{3,}").isEmpty()) { // 4 matching characters
            return HandResult.FOUR_OF_A_KIND;
        }
        // checking for full House
        String fullHouse = sortedString;
        fullHouse = fullHouse.replaceAll(matchRegex(sortedString,"(.)\\1{2,}"),"");
        boolean isFullHouse = false;
        if(fullHouse.length() == 2) {
            isFullHouse = fullHouse.charAt(0) == fullHouse.charAt(1);
        }

        if(isFullHouse) {
            return HandResult.FULL_HOUSE;
        } 
        if (!matchRegex(sortedString,"(.)\\1{2,}").isEmpty()) { // 3 matching characters
                return HandResult.THREE_OF_A_KIND;            
        }

        Pattern twoPairPattern = Pattern.compile("(.)\\1");
        Matcher matcher = twoPairPattern.matcher(sortedString);
        boolean firstFind = matcher.find();

        boolean secondFind = matcher.find();
        if (firstFind && secondFind) { // two Pair
            return HandResult.TWO_PAIR;
        }
        if(firstFind) { // One Pair
            return HandResult.ONE_PAIR;
        }
        return HandResult.HIGH_CARD;
    }

    private static String matchRegex(String s, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(s);
        boolean found = matcher.find();
        if(found) {
            return matcher.group();
        } else {
            return "";
        }
    }


}
