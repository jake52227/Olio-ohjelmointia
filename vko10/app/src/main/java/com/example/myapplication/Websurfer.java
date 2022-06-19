package com.example.myapplication;

public interface Websurfer {
    int loadNewPage();
    int loadNextPage();
    int loadPreviousPage();
    int refreshCurrentPage();
    String getSearchBarText();
}
