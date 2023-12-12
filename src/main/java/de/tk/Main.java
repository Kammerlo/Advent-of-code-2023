package de.tk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        
        String test = "AAAABB  ";
        String regex = "(.)\\1{4,}";


        System.out.println(test.matches(regex));

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(test);
        boolean find = matcher.find();
        String group = matcher.group();
        test = test.replaceAll(group,"");
        System.out.println(test);
        System.out.println(find);
        System.out.println(group);
    }
}
