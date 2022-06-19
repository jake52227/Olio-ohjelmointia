// Jaakko Pyrhönen 19.6.2022

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Websurfer ws;
    private JavaScriptHandler jsh;
    private Button jsInit;
    private Button jsSHout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);

        EditText searchBar = (EditText) findViewById(R.id.searchBarInput);

        searchBar.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Täältä apua EditorInfo-kohtaan, jotta haku toimii myös puhelimella: https://stackoverflow.com/questions/2004344/how-do-i-handle-imeoptions-done-button-click
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBar.requestFocus();
                    loadPage(v);
                    return true;
                }
                return false;
            }
        });

        ws = new WebsiteSurfer(new WebsiteLoader(wv), new WebsiteHistory(15), searchBar);
        jsh = new JavaScriptHandler(wv);
        jsh.enableJS();

        jsInit = (Button) findViewById(R.id.jsInitialize);
        jsSHout = (Button) findViewById(R.id.jsShoutout);

    }

    public void nextPageClick(View v) {
        int rc = ws.loadNextPage();
        if (rc == -1) {
            Toast toast = Toast.makeText(MainActivity.this, "no next page available", Toast.LENGTH_SHORT);
            toast.show();
        } else if (rc == 0) {
            jsInit.setVisibility(v.INVISIBLE);
            jsSHout.setVisibility(v.INVISIBLE);
        } else if (rc == 1) {
            jsInit.setVisibility(v.VISIBLE);
            jsSHout.setVisibility(v.VISIBLE);
        }
    }

    public void previousPageClick(View v) {
        int rc = ws.loadPreviousPage();
        if (rc == -1) {
            Toast toast = Toast.makeText(MainActivity.this, "no previous page available", Toast.LENGTH_SHORT);
            toast.show();
        } else if (rc == 0) {
            jsInit.setVisibility(v.INVISIBLE);
            jsSHout.setVisibility(v.INVISIBLE);
        } else if (rc == 1) {
            jsInit.setVisibility(v.VISIBLE);
            jsSHout.setVisibility(v.VISIBLE);
        }
    }

    public void pageRefreshClick(View v) {
        int rc = ws.refreshCurrentPage();
        if (rc == -1) {
            Toast toast = Toast.makeText(MainActivity.this, "no page to refresh", Toast.LENGTH_SHORT);
            toast.show();
        } else if (rc == 0) {
            jsInit.setVisibility(v.INVISIBLE);
            jsSHout.setVisibility(v.INVISIBLE);
        } else if (rc == 1) {
            jsInit.setVisibility(v.VISIBLE);
            jsSHout.setVisibility(v.VISIBLE);
        }
    }


    public void loadPage(View v) {
        if (ws.loadNewPage() == 1) {
            jsInit.setVisibility(v.VISIBLE);
            jsSHout.setVisibility(v.VISIBLE);
        } else {
            jsInit.setVisibility(v.INVISIBLE);
            jsSHout.setVisibility(v.INVISIBLE);
        }
    }

    public void jsInit(View v) {
        jsh.executeJS("initialize()");
    }

    public void jsShoutout(View v) {
        jsh.executeJS("shoutOut()");
    }

}