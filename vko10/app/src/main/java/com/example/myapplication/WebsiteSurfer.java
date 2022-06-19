package com.example.myapplication;

import android.widget.EditText;

public class WebsiteSurfer implements Websurfer {

    private SiteLoader siteLoader;
    private WebHistory webHistory;
    private EditText searchBar;

    public WebsiteSurfer(SiteLoader sl, WebHistory wh, EditText searchBar) {
        this.siteLoader = sl;
        this.webHistory = wh;
        this.searchBar = searchBar;

    }

    @Override
    public int loadNewPage() {
        String url = getSearchBarText();
        if (!url.isEmpty()) {
            webHistory.clearFromCurrentToEnd();
            webHistory.addWebsite(new Site(url));
            siteLoader.loadSite(webHistory.getCurrentUrl());

            if (url.contains("html")) {
                return 1;
            }

        }
        return 0;
    }

    @Override
    public int loadNextPage() {
       String url = webHistory.getNextUrl();
       if (url != null) {
           siteLoader.loadSite(url);
           if (url.contains("html")) {
               return 1;
           }
           return 0;
       }
       return -1;
    }

    @Override
    public int loadPreviousPage() {
        String url = webHistory.getPreviousUrl();
        if (url != null) {
            siteLoader.loadSite(url);
            if (url.contains("html")) {
                return 1;
            }
            return 0;
        }
        return -1;
    }

    @Override
    public int refreshCurrentPage() {
        String url = webHistory.getCurrentUrl();
        if (url != null) {
            siteLoader.loadSite(url);
            if (url.contains("html")) {
                return 1;
            }
            return 0;
        }
        return -1;
    }

    @Override
    public String getSearchBarText() {
        return searchBar.getText().toString();
    }

}
