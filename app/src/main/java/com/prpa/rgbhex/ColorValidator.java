package com.prpa.rgbhex;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

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
    Context context;
    //endregion

    public ColorValidator(Context c){
        rgbColor=oneColorValidator+dot+oneColorValidator+dot+oneColorValidator;
        rgbaColor=rgbColor+dot+percentColorValidator;
        argbColor=rgbColor+dot+oneColorValidator;
        allColors=hashColorValidator+"|"+rgbaColor+"|"+argbColor+"|"+rgbColor;
        context=c;
    }

    public int getColor(String s) {

        if (s.length() > 25) {
            Toast.makeText(context, context.getText(R.string.color_to_long), Toast.LENGTH_SHORT);
            return Color.argb(255, 0, 0, 0);
        }


        Pattern pattern = Pattern.compile(allColors);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            Pattern p = Pattern.compile(hashColorValidator);
            Matcher m = p.matcher(matcher.group());

            if (m.find()) return Color.parseColor("#" + matcher.group());
        //} else {

            Pattern pp = Pattern.compile(oneColorValidator);
            Matcher mm = pp.matcher(matcher.group());
            int r;
            int g;
            int b;
            int a;
            mm.find();
            a = Integer.parseInt(mm.group());
            mm.find();
            r = Integer.parseInt(mm.group());
            mm.find();
            g = Integer.parseInt(mm.group());
            mm.find();

            try {
                b = Integer.parseInt(mm.group());
            } catch (NumberFormatException e) {
                try {
                    float f = Float.parseFloat(mm.group());
                    b = g;
                    g = r;
                    r = a;
                    if (f <= 1 && f >= 0) a = (int) (255 * f);
                    else a = 1;
                    return Color.argb(a, r, g, b);
                } catch (NumberFormatException ex) {
                    Toast.makeText(context, context.getResources().getText(R.string.color_wrong_format), Toast.LENGTH_SHORT);
                    return Color.rgb(0, 0, 0);
                }
            }
            if(s.contains("rgba")){
            return Color.argb(255,a,r,g);
            }else
            return Color.argb(a, r, g, b);


        }

        return Color.rgb(0, 0, 0);
    }

}
