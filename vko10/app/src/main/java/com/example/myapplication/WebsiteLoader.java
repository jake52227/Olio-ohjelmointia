package com.example.myapplication;


import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsiteLoader implements SiteLoader {

    private WebView webView;

    public WebsiteLoader(WebView v) {
        this.webView = v;
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void loadSite(String url) {
        webView.loadUrl(url);
    }

}
