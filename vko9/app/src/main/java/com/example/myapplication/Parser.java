package com.example.myapplication;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Parser implements XMLParser {

    // hakee annetusta url-osoitteesta tagin sisältämät tiedot, lisää ne NodeListaan ja palauttaa listan
    @Override
    public NodeList readToList(String url, String tagName)  {
        NodeList nList = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            nList = doc.getDocumentElement().getElementsByTagName(tagName);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return nList;
    }
}
