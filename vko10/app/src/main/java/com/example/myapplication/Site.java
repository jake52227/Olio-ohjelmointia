package com.example.myapplication;

public class Site implements Website {
    private String url;

    public Site(String url) {
        if (url.contains("http://")) {
            this.url = url;
        } else if (!url.contains(".html")) {
            this.url = "http://" + url;
        } else {
            this.url = "file:///android_asset/" + url;
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        if (url.contains("http://")) {
            this.url = url;
        } else {
            this.url = "http://" + url;
        }
    }

}
