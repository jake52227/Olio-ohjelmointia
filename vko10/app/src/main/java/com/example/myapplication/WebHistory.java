package com.example.myapplication;

public interface WebHistory {
    void addWebsite(Website site);
    String getCurrentUrl();
    String getPreviousUrl();
    String getNextUrl();
    void clearFromCurrentToEnd();
}
