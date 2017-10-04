package com.capton.enb;


import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * Created by capton on 2017/9/28.
 */

public class TextSizeUtil {
    public static float getTextWidth(Context context, String text, int textSize){
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

    public static int getTextHeight(int textSize){
        Paint p = new Paint();
        p.setTextSize(textSize);
        p.setAntiAlias(true);
        Paint.FontMetrics fm = p.getFontMetrics();
        return  (int) (Math.ceil(fm.descent - fm.ascent)+2);
       // System.out.println("textHeight = "  + textHeight);
    }
}
