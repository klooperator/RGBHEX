package com.prpa.rgbhex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prpa on 06.02.2015.
 */
public class Test {

    public void init(){
        Scanner in=new Scanner(System.in);
        System.out.println("Your color sir:");
        parseAnyColor(in.next());
    }

    public void parseAnyColor(String s){
        System.out.println("Parsing your shit sir...");

        String test="^(a[0-9])$";

        String start="^";
        String hashColorValidator="([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})";
        String oneColorValidator="([01]?\\d?\\d|2[0-4]\\d|25[0-5])";
        String dot="\\.|\\,";
        String end="$";
        String rgbColorValidator=oneColorValidator+dot+oneColorValidator+dot+oneColorValidator;

        String regexValidator=start+hashColorValidator+end;

        Pattern pattern=Pattern.compile(test);
        //Pattern pattern=Pattern.compile(regexValidator);
        Matcher matcher=pattern.matcher(s);
        //System.out.println(regexValidator);
        int i=0;
        while(matcher.find()){
            System.out.println("Found your shit sir...");
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            //System.out.println(matcher.group(2));
            i++;
        }
		/*if(matcher.find()){

			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			if(matcher.groupCount()>2)System.out.println(matcher.group(2));
		}*/
        matcher.reset();
        init();

    }
}
