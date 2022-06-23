package com.example.vko11;

import android.graphics.Typeface;

public class SettingsTranslator implements SettingsTranslationInterface {

    public int getSelectedColourByIndex(int index) {
        switch (index) {
            case 1:
                return R.color.purple_200;
            case 2:
                return R.color.purple_500;
            case 3:
                return R.color.purple_700;
            case 4:
                return R.color.teal_200;
            case 5:
                return R.color.teal_700;
            case 6:
                return R.color.white;
        }
        return R.color.black;
    }

    public int getSelectedTextStyle() {
        switch (Settings.getInstance().getTextStyle()) {
            case "bold":
            case "lihavoitu":
                return Typeface.BOLD;
            case "italic":
            case "kursivoitu":
                return Typeface.ITALIC;
        }
        return Typeface.NORMAL;
    }


    public String getLanguageByID(int languageID) {
        String language = "en";
        switch (languageID) {
            case 0:
                language = "en";
                break;
            case 1:
                language = "fin";
                break;
            case 2:
                language = "swe";
                break;
        }
        return language;
    }

}
