package com.capton.enbdemo;

import java.io.Serializable;

/**
 * Created by capton on 2017/10/4.
 */

public class Settings implements Serializable{
    int width;
    int height;
    int textMarginTop;
    int textMarginBottom;
    int iconMarginTop;
    int iconMarginBottom;
    int textSize;
    int iconWidth;
    int iconHeight;
    int textColor;
    int dividerColor;
    int checkTextColor;
    boolean showDivider;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public int getCheckTextColor() {
        return checkTextColor;
    }

    public void setCheckTextColor(int checkTextColor) {
        this.checkTextColor = checkTextColor;
    }

    public int getTextMarginTop() {
        return textMarginTop;
    }

    public void setTextMarginTop(int textMarginTop) {
        this.textMarginTop = textMarginTop;
    }

    public int getTextMarginBottom() {
        return textMarginBottom;
    }

    public void setTextMarginBottom(int textMarginBottom) {
        this.textMarginBottom = textMarginBottom;
    }

    public int getIconMarginTop() {
        return iconMarginTop;
    }

    public void setIconMarginTop(int iconMarginTop) {
        this.iconMarginTop = iconMarginTop;
    }

    public int getIconMarginBottom() {
        return iconMarginBottom;
    }

    public void setIconMarginBottom(int iconMarginBottom) {
        this.iconMarginBottom = iconMarginBottom;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public boolean isShowDivider() {
        return showDivider;
    }

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
    }
}
