package com.example.myapplication;

import java.util.ArrayList;

public class WebsiteHistory implements WebHistory {

    private ArrayList<Website> sites;
    private int currentIndex;
    private int historySizeLimit;

    public WebsiteHistory(int historySizeLimit) {
        sites = new ArrayList<>();
        currentIndex = -1;
        this.historySizeLimit = historySizeLimit;
    }

    @Override
    public void addWebsite(Website site) {
        if (!site.getUrl().equals(getCurrentUrl())) {
            sites.add(site);
            currentIndex++;
        }
        if (currentIndex + 1 > historySizeLimit) {
            sites.remove(0);
            currentIndex--;
        }
    }

    @Override
    public String getCurrentUrl() {
        if (currentIndex >= 0) {
            return sites.get(currentIndex).getUrl();
        }
        return null;
    }

    @Override
    public String getPreviousUrl() {
        if (currentIndex > 0) {
            currentIndex--;
            return sites.get(currentIndex).getUrl();
        }
        return null;
    }

    @Override
    public String getNextUrl() {
        if (currentIndex < sites.size() - 1) {
            currentIndex++;
            return sites.get(currentIndex).getUrl();
        }
        return null;
    }

    @Override
    public void clearFromCurrentToEnd() {
        if (currentIndex >= 0) {
            int indexOfLast = sites.size() - 1;
            while (indexOfLast > currentIndex) {
                sites.remove(indexOfLast);
                indexOfLast--;
            }
        }
    }
}
