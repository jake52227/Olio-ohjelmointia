package com.example.vko11;

// kaikki tähän käyttöliittymään liittyvä saanut inspiraationsa Harlo Holmesin vastauksesta: https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity

public interface SideBarInterface {
    public void openMenu();
    public void closeMenu();
    public void notifyBasicSettingsChange();
    public void notifyLanguageChange();
}
