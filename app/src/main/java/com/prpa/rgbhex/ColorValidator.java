package com.prpa.rgbhex;

import android.graphics.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prpa on 08.02.2015.
 */
public class ColorValidator {

    //region Globals
    public static final String hashColorValidator="([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})";
    public static final String oneColorValidator="([01]?\\d?\\d|2[0-4]\\d|25[0-5])";
    public static final String percentColorValidator="((0\\.[0-9]{1,2})|1)";
    public static final String dot="[\\,]";

    private String allColors;
    private String rgbColor;
    private String argbColor;
    private String rgbaColor;
    //endregion

    public ColorValidator(){
        rgbColor=oneColorValidator+dot+oneColorValidator+dot+oneColorValidator;
        rgbaColor=rgbColor+dot+percentColorValidator;
        argbColor=rgbColor+dot+oneColorValidator;
        allColors=hashColorValidator+"|"+rgbaColor+"|"+argbColor+"|"+rgbColor;
    }

    public int getColor(String s){
        Pattern pattern=Pattern.compile(allColors);
        Matcher matcher=pattern.matcher(s);

        if (matcher.find()) {
            Pattern p = Pattern.compile(oneColorValidator);
            Matcher m = p.matcher(matcher.group());
            int r;
            int g;
            int b;
            int a;

            


           /* switch (matcher.group().length()) {

                case 6:
                    return Color.parseColor("#" + matcher.group());
                case 8:
                    return Color.parseColor("#" + matcher.group());
                case 11:
                    m.find();
                    r = Integer.parseInt(m.group());
                    m.find();
                    g = Integer.parseInt(m.group());
                    m.find();
                    b = Integer.parseInt(m.group());
                    return Color.rgb(r, g, b);
                case 15:

                    m.find();
                    a = Integer.parseInt(m.group());
                    m.find();
                    r = Integer.parseInt(m.group());
                    m.find();
                    g = Integer.parseInt(m.group());
                    m.find();
                    b = Integer.parseInt(m.group());
                    return Color.argb(a, r, g, b);
            }*/

        }

        return Color.rgb(0,0,0);
    }

}
