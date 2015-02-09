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

    public int getColor(String s){
        Pattern pattern=Pattern.compile(allColors);
        Matcher matcher=pattern.matcher(s);

        if (matcher.find()) {
            Pattern p = Pattern.compile(hashColorValidator);
            Matcher m = p.matcher(matcher.group());
            Pattern pp = Pattern.compile(oneColorValidator);
            Matcher mm = p.matcher(matcher.group());
            int r;
            int g;
            int b;
            int a;

            if(m.find()){
				return Color.parseColor("#"+matcher.group());
			}else {
                if(s.contains("argb")){
                    mm.find();
                    a = Integer.parseInt(m.group());
                    mm.find();
                    r = Integer.parseInt(m.group());
                    mm.find();
                    g = Integer.parseInt(m.group());
                    mm.find();
                    try{
                        b = Integer.parseInt(m.group());
                    }catch (NumberFormatException e){
                        b=a;
                        a=255;
                        Toast.makeText(context,context.getResources().getText(R.string.color_wrong_format),Toast.LENGTH_SHORT);
                    }

                    return Color.argb(a, r, g, b);
                }else if(s.contains("rgba")){

                }else{

                }
				
			}


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
