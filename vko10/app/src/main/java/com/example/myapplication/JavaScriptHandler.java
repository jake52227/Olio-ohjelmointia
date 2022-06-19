package com.example.myapplication;

import android.webkit.WebView;

public class JavaScriptHandler {
    private WebView webView;

    public JavaScriptHandler(WebView wv) {
        webView = wv;
    }

    public void enableJS() {
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public void executeJS(String function) {
        if (!function.isEmpty()) {
            webView.evaluateJavascript("javascript:"+function, null);
        }
    }
}
