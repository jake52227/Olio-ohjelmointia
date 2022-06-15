package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentFilter {

    public ArrayList<String> filterContentsByTime(ArrayList<String> currentList, String startTime, String endTime) {
        ArrayList<String> filteredList = new ArrayList<>();

        int startMinutes = parseTimeToMinutes(startTime);
        int endMinutes = parseTimeToMinutes(endTime);

        for (String prefixAndTime : currentList) {
            String timeString = prefixAndTime.split(",")[1];
            String[] times = timeString.split("-");

            if (times.length == 2) {
                String startsAt = times[0].trim();
                String endsAt = times[1].trim();
                if ( (startMinutes <= parseTimeToMinutes(startsAt) ) && (endMinutes >= parseTimeToMinutes(endsAt) ) ) {
                    filteredList.add(prefixAndTime);
                }
            }
        }
        return filteredList;
    }

    private int parseTimeToMinutes(String timeString) {
        String parsed = parseTime(timeString);
        String[] split = parsed.split(":");
        String hours = split[0];
        String minutes = split[1];
        return Integer.parseInt(hours) * 60 + Integer.parseInt(minutes);
    }

    public String parseTime(String time) {
        if (time.contains("T")) {
            return time.split("T")[1];
        }
        return time;
    }

    public ArrayList<String> filterTheatresByMovie(MovieList currentMovieList, TheatresList theatresList ,String movie, String date, String currentTheatreSelection) {
        ArrayList<String> theatreAndTime = new ArrayList<>();
        boolean searchAll = false;

        if (currentTheatreSelection.equals("NO SELECTION")) {
            searchAll = true;
        }

        // jos valinta teatterista, etsitään kyseisen teatterin tarjonnasta
        if (!searchAll) {
            for (Movie m : currentMovieList.getList()) {
                if (m.getName().equals(movie)) {
                    Theatre t = theatresList.getTheatreByID(Integer.parseInt(m.getTheatreID()));
                    String startTime = parseTime(m.getShowStart());
                    String endTime = parseTime(m.getShowEnd());
                    String toAdd = t.getPlace() + ", " + startTime + " - " + endTime;
                    theatreAndTime.add(toAdd);
                }

            }
        } else {
            for (Theatre t : theatresList.getTheatreList()) {
                if (t.getPlace().equals("NO SELECTION")) {
                    continue;
                }
                // muutoin joudutaan hakemaan aina uudet tiedot teatterien elokuvista ensin:
                currentMovieList.clearList();
                currentMovieList.populateListByDate(new Parser(), t.getId(), date);
                for (Movie m : currentMovieList.getList()) {
                    if (m.getName().equals(movie)) {
                        String startTime = parseTime(m.getShowStart());
                        String endTime = parseTime(m.getShowEnd());
                        String toAdd = t.getPlace() + ", " + startTime + " - " + endTime;
                        theatreAndTime.add(toAdd);
                    }
                }
            }
        }
        return theatreAndTime;
    }

}
