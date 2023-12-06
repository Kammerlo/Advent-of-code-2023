package de.tk.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;

class DigitPosition {
    char digit;
    int position;
}

public class Main {

    static String[] DIGITS_AS_WORDS = {"one", "two", "three","four","five","six","seven","eight","nine"};

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // firstTask();
        secondTask();
    }

    private static void secondTask() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day1\\input1.txt"));
            String line = reader.readLine();
            int sum = 0;
            while(line != null) {
                char[] lineAsCharArray = line.toCharArray();
                DigitPosition firstDigitPosition = getFirstDigitInCharArray(lineAsCharArray);
                ArrayUtils.reverse(lineAsCharArray);
                DigitPosition lastDigitPosition = getFirstDigitInCharArray(lineAsCharArray);
                lastDigitPosition.position = line.length() - lastDigitPosition.position - 1; // Starting the array at 0
                // a little bit strange, but it reduces the tiping overhead for now and does the trick :)             
                ArrayUtils.reverse(lineAsCharArray);
                for(int i = 0; i < DIGITS_AS_WORDS.length; i ++) {
                    String numberAsWord = DIGITS_AS_WORDS[i];

                    int first = line.indexOf(numberAsWord);
                    if(first < firstDigitPosition.position && first >= 0) {
                        firstDigitPosition.position = first;
                        firstDigitPosition.digit = (char)(i + 1 + 48);
                    }

                    int last = line.lastIndexOf(numberAsWord);
                    if(last > lastDigitPosition.position && last >= 0) {
                        lastDigitPosition.position = last;
                        lastDigitPosition.digit = (char)(i + 1 + 48);
                    }
                }
                try {
                    sum += Integer.valueOf(firstDigitPosition.digit + "" + lastDigitPosition.digit);
                } catch(NumberFormatException e) {
                    System.out.println("Something went wrong");
                }
                line = reader.readLine();
            }
            reader.close();
            System.out.println("The number is: " + sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void firstTask() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\de\\tk\\day1\\input1.txt"));
            String line = reader.readLine();
            int sum = 0;
            while(line != null) {
                char[] lineAsCharArray = line.toCharArray();
                DigitPosition firstNumber = getFirstDigitInCharArray(lineAsCharArray);
                ArrayUtils.reverse(lineAsCharArray);
                DigitPosition lastNumber = getFirstDigitInCharArray(lineAsCharArray);
                sum += Integer.valueOf(firstNumber.digit + "" + lastNumber.digit);
                line = reader.readLine();
            }
            reader.close();
            System.out.println("The number is: " + sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static DigitPosition getFirstDigitInCharArray(char[] arr) {
        DigitPosition digitPosition = new DigitPosition();
        int pos = 0;
        for(char ch : arr) {
            if(Character.isDigit(ch)) {
                digitPosition.digit = ch;
                digitPosition.position = pos;
                break;
            }
            pos++;
        }
        return digitPosition;
    }
}
