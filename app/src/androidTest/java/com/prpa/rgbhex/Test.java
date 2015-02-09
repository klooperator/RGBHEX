package com.prpa.rgbhex;

import java.util.ArrayList;
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
		// parseAnyColor(in.next());
		//parseAnyColor("ghj A7 fA8 bfftA9fgbd");
		ArrayList<String> list=new ArrayList<String>();
		list.add("scvhdfvrsaf5dB1klydvbdefv cdfghcbj cdshjF63a5Cjjjj");
		list.add("cbbderh ddyyaaab99564gb fs233.456.33#$&&");
		list.add("x12.56,78$$$fvhj.127,44,0===gbxd=");
		list.add("Af3Cd466::: 24 67 88 +-%##&-'gdghvvdfh 26,0.3,56&&&");
		int i=1;
		for(String a:list){
			System.out.println("RUN..."+i);
			parseAnyColor(a);
			i++;
		}
		//parseAnyColor("");
    }

    public void parseAnyColor(String s){
        System.out.println("Parsing your shit sir...");

        String test="(A[4-9])";

        String start="^";
        String hashColorValidator="([A-Fa-f0-9]{8}|[A-Fa-f0-9]{8})";
        String oneColorValidator="([01]?\\d?\\d|2[0-4]\\d|25[0-5])";
        String dot="[\\,]";
        String end="$";
        String rgbColorValidator=oneColorValidator+dot+oneColorValidator+dot+oneColorValidator;

        String regexValidator=start+hashColorValidator+end;

        Pattern pattern=Pattern.compile(hashColorValidator+"|"+
				oneColorValidator+dot+oneColorValidator+dot+oneColorValidator+dot+oneColorValidator+"|"+
                oneColorValidator+dot+oneColorValidator+dot+oneColorValidator);
        //Pattern pattern=Pattern.compile(regexValidator);
        Matcher matcher=pattern.matcher(s);
        //System.out.println(regexValidator);
        System.out.println("test string="+s);
		int i=1;
        while(matcher.find()){
            System.out.println("**Found your shit sir..***.."+i);
            System.out.println("group()="+matcher.group());
            //System.out.println("group(0)="+matcher.group(0));
			//System.out.println("group(1)="+matcher.group(1));
            //System.out.println(matcher.group(2));
            i++;
        }
		/*if(matcher.find()){

		 System.out.println(matcher.group(0));
		 System.out.println(matcher.group(1));
		 if(matcher.groupCount()>2)System.out.println(matcher.group(2));
		 }*/
        //matcher.reset();
        //init();

    }
}
