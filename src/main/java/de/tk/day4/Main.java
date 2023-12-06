package de.tk.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class Card {
    List<Integer> numbers = new ArrayList<>();
    List<Integer> winningNumbers = new ArrayList<>();
    int value;
    int matchCount = 0;
    int copies = 1;
}




public class Main {

    static List<Integer> winningNumbersList = new ArrayList<>();
    static List<Card> cards = new ArrayList<>();

    public static void main(String[] args) {
        fillLists();

        calculateCardValue(); 
        int result = addAllCardValues(); // until here is task 1


        result = countCardsAndCopies();
        System.out.println(result);
    }

    private static int countCardsAndCopies() {
        int result = 0;
        for (Card card : cards) {
            result = result + card.copies;
        }
        return result;
    }

    private static int addAllCardValues() {
        int result = 0;
        for (Card card : cards) {
            result = result + card.value;
        }
        return result;
    }

    private static void calculateCardValue() {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            for (int j : card.numbers) {
                if(card.winningNumbers.contains(j)) {
                    if(card.value == 0) {
                        card.value = 1;
                    } else {
                        card.value = card.value * 2;
                    }
                    card.matchCount++;
                }
            }
            //card.value = card.value * card.copies;
            for(int k = 0; k < card.copies; k++) { // adding copies for cards
                addCopiesForFollowingCards(i, card);
            }
            cards.set(i, card);
        }
    }

    private static void addCopiesForFollowingCards(int i, Card card) {
        for(int o = 1; o <= card.matchCount;o++) {
            Card cardCopies = cards.get(i + o);
            cardCopies.copies += 1;
            cards.set(i, cardCopies);
        }
    }

    private static void fillLists() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day4\\input.txt"));
            String line = reader.readLine();
            while(line != null) {
                String card = line.replaceAll("Card\\s{1,}\\d{1,}:\\s", "");
                String[] cardSplit = card.split("\\|");

                // winningNumbersList.addAll(convertCardNumbersToList(cardSplit[0]));
                Card c = new Card();
                c.winningNumbers = convertCardNumbersToList(cardSplit[0]);
                c.numbers = convertCardNumbersToList(cardSplit[1]);
                cards.add(c);

                
                line = reader.readLine();
            }
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> convertCardNumbersToList(String numbeString) {
        List<Integer> list = new ArrayList<>();

        String[] split = numbeString.split("\\s");
        for(String s : split) {
            if(!s.isEmpty()) 
                list.add(Integer.valueOf(s).intValue());
        }
        return list;
    }

}