package com.example.myapplication;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.temporal.Temporal;
import java.util.ArrayList;

public class TheatresList {
    private ArrayList<Theatre> theatres;

    public TheatresList() {
        this.theatres = new ArrayList<>();
    }

    // hakee paikkatiedot teattereille ja laittaa tulokset listaan
    public void populateList(XMLParser parser) {
        NodeList theatreAreas = parser.readToList("https://www.finnkino.fi/xml/TheatreAreas/", "TheatreArea");
        this.theatres.add(new Theatre("NO SELECTION", -1));
        for (int i = 0; i < theatreAreas.getLength(); i++) {
            Node node = theatreAreas.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String place = element.getElementsByTagName("Name").item(0).getTextContent();
                if (place.contains(":")) {
                    Integer id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    this.theatres.add(new Theatre(place, id));
                }
            }
        }
    }

    public String[] getPlacesAsStringArray() {
        String[] items = new String[this.theatres.size()];
        for (int i = 0; i < this.theatres.size(); i++) {
            items[i] = this.theatres.get(i).getPlace();
        }
        return items;
    }

    public ArrayList<Theatre> getTheatreList() {
        return this.theatres;
    }

    public Theatre getTheatreByName(String name) {
        for (Theatre t : this.getTheatreList()) {
            if (name.equals(t.getPlace())) {
                return t;
            }
        }
        return null;
    }

    public Theatre getTheatreByID(int id) {
        for (Theatre t : this.getTheatreList()) {
            if (id == t.getId()) {
                return t;
            }
        }
        return null;
    }
}