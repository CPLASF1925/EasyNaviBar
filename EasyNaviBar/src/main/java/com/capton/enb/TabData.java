package com.capton.enb;

/**
 * Created by capton on 2017/9/16.
 */

public class TabData {
    public int iconRes;
    public int checkIconRes;
    public String text;

    public TabData(int iconRes,int checkIconRes,String text) {
        this.iconRes = iconRes;
        this.text = text;
        this.checkIconRes=checkIconRes;
    }
}
