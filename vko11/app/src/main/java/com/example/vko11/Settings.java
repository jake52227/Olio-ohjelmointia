package com.example.vko11;


public class Settings {
    private boolean textIsEditable = false;
    private int textFontSize = 14;
    private int textColor = R.color.black;
    private String fontFamily = "sans-serif";
    private String textStyle = "default";
    private int languageID = 0;

    private static Settings settings = new Settings();

    public static Settings getInstance() {
        return settings;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextIsEditable(boolean b) {
        this.textIsEditable = b;
    }

    public boolean getTextIsEditable() {
        return this.textIsEditable;
    }

}
