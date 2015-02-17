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
    public static final String oneColorValidator="(2[0-4]\\d|25[0-5]|[01]?\\d?\\d)";
    public static final String percentColorValidator="(0\\.[0-9]{1,2})";
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
            //Toast.makeText(context, context.getText(R.string.color_to_long), Toast.LENGTH_SHORT).show();
            return Color.argb(255, 0, 0, 0);
        }


        Pattern pattern = Pattern.compile(allColors);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            Pattern p = Pattern.compile(hashColorValidator);
            Matcher m = p.matcher(matcher.group());
			//Toast.makeText(context,"found something",Toast.LENGTH_SHORT).show();
            if (m.find()) return Color.parseColor("#" + matcher.group());
        //} else {

            Pattern pp = Pattern.compile(percentColorValidator+"|"+oneColorValidator);
            Matcher mm = pp.matcher(matcher.group());
            /*int r;
            int g;
            int b;
            int a;*/
			int first=0,second=0,third=0,fourth=0;
            mm.find();
            first = Integer.parseInt(mm.group());
            mm.find();
            second = Integer.parseInt(mm.group());
            mm.find();
            third = Integer.parseInt(mm.group());
            if(mm.find()){
			//Toast.makeText(context,"foun fourth number="+first+","+second+","+third,Toast.LENGTH_LONG).show();
            try {
                fourth = Integer.parseInt(mm.group());
				
				if(s.contains("rgba") && fourth==1) {
				//	Toast.makeText(context,"reached it....",Toast.LENGTH_LONG).show();
					return Color.argb(255,first,second,third);
				
				}
				else return Color.argb(first,second,third,fourth);
            } catch (NumberFormatException e) {
                try {
                    float f = Float.parseFloat(mm.group());
                    /*b = g;
                    g = r;
                    r = a;*/
                    if (f <= 1 && f >= 0) fourth = (int) (255 * f);
                    else fourth = 1;
                    return Color.argb(fourth, first, second, third);
                } catch (NumberFormatException ex) {
                    Toast.makeText(context, context.getResources().getText(R.string.color_wrong_format), Toast.LENGTH_SHORT).show();
                    return Color.rgb(first, second, third);
                }
            }
			}else return Color.rgb(first,second,third);
			
           /* if(s.contains("rgba")){
            return Color.argb(255,a,r,g);
            }else
            return Color.argb(a, r, g, b);*/


        }

        return Color.rgb(1, 0, 1);
    }

}
