package com.example.myapplication;

import org.w3c.dom.NodeList;

// kokeilin vähän käänteisen riippuvuuden periaatetteen noudattamista
public interface XMLParser {
    public NodeList readToList(String url, String tagName);
}
