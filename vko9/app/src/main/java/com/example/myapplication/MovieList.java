package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;


public class MovieList {
    private ArrayList<Movie> movies;

    public MovieList() {
        this.movies = new ArrayList<>();
    }

    public ArrayList<Movie> getList() {
        return this.movies;
    }


    public String checkDateStringFormat(String date) {
        String result = "";
        String[] parts = date.split("\\.");
        if (parts[0].length() != 2) {
            result += "0"+parts[0];
        } else {
            result += parts[0];
        }
        for (int i = 1; i < parts.length - 1; i++) {
            if (parts[i].length() != 2) {
                result += ".0"+parts[i];
            } else {
                result += "."+parts[i];
            }
        }
        result += "." + parts[parts.length - 1];
        System.out.println(result);
        return result;
    }

    public void populateListByDate(XMLParser parser, int theatreID, String date) {
        date = checkDateStringFormat(date);
        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + date;
        NodeList shows = parser.readToList(url, "Show");

        for (int i = 0; i < shows.getLength(); i++) {
            Node node = shows.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getElementsByTagName("Title").item(0).getTextContent();
                String theatre = element.getElementsByTagName("Theatre").item(0).getTextContent();
                String startTime = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                String endTime = element.getElementsByTagName("dttmShowEnd").item(0).getTextContent();

                this.movies.add(new Movie(date, name, theatre, startTime, endTime));

            }
        }
    }

    public void clearList() {
        this.getList().clear();
    }

    public ArrayList<String> getMoviesAsStrings(ContentFilter filter) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Movie m : this.getList()) {
            arrayList.add(formatMovie(m, filter));
        }
        return arrayList;
    }

    public Movie getMovieByName(String name) {
        for (Movie m : this.getList()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public String formatMovie(Movie m, ContentFilter filter) {
        return m.getName() + ", " + filter.parseTime(m.getShowStart()) + " - " + filter.parseTime(m.getShowEnd());
    }
}
