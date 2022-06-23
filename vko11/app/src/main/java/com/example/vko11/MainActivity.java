package com.example.vko11;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.KeyListener;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;

import java.util.Locale;

// Jaakko Pyrhönen 23.6.2022

public class MainActivity extends AppCompatActivity implements SideBarInterface {

    private ImageButton menuButton;
    private SidebarFragment sidebarFragment = null;
    private EditText editableText;
    private KeyListener editableTextListener;
    private TextView readableText;
    private TextView displayText;
    private SettingsTranslationInterface settingsTranslator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuButton = (ImageButton) findViewById(R.id.sideBarMenuButton);
        editableText = (EditText) findViewById(R.id.editableText);
        editableTextListener = (KeyListener) editableText.getKeyListener();
        readableText = (TextView) findViewById(R.id.readableText);
        displayText = (TextView) findViewById(R.id.displayText);
        settingsTranslator = new SettingsTranslator();

        editableText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (Settings.getInstance().getTextIsEditable()) {
                    editableText.setKeyListener(editableTextListener);
                } else {
                    editableText.setKeyListener(null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setTexts();
        updateElements();
    }

    @Override
    public void notifyBasicSettingsChange() {
        updateElements();
    }

    public void openMenuButton(View v) {
        openMenu();
    }

    @Override
    public void openMenu() {
        hideBasicElements();

        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (sidebarFragment == null) {
            sidebarFragment = new SidebarFragment();
            transaction.replace(R.id.sideBarWindow, sidebarFragment);
            transaction.commit();
        } else {
            transaction.show(sidebarFragment);
            transaction.commit();
        }
    }

    @Override
    public void closeMenu() {
        if (!Settings.getInstance().getTextIsEditable()) {
            updateText();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(sidebarFragment);
        transaction.commit();
        showBasicElements();
    }

    private void updateText() {
        TextState.getInstance().setReadableText(editableText.getText().toString());
        readableText.setText(TextState.getInstance().getReadableText());
    }

    private void updateElements() {
        Settings st = Settings.getInstance();
        TextState ts = TextState.getInstance();

        if (!st.getTextIsEditable()) {
            updateText();
        }

        // lähde koodinpätkälle väriä varten käyttäjän "sat" vastaus: https://stackoverflow.com/questions/5271387/how-can-i-get-color-int-from-color-resource
        readableText.setTextColor(ResourcesCompat.getColor(getResources(), st.getTextColor(), null));
        readableText.setTextSize((float)st.getTextFontSize());

        // lähde TypeFace-koodille käyttäjän "manochar" vastaus: https://stackoverflow.com/questions/12128331/how-to-change-fontfamily-of-textview-in-android
        readableText.setTypeface(Typeface.create(st.getFontFamily(), Typeface.NORMAL), settingsTranslator.getSelectedTextStyle());
        displayText.setText(ts.getDisplayText());
    }

    private void hideBasicElements() {
        editableText.setVisibility(View.INVISIBLE);
        readableText.setVisibility(View.INVISIBLE);
        displayText.setVisibility(View.INVISIBLE);
        menuButton.setVisibility(View.INVISIBLE);
    }

    private void showBasicElements() {
        editableText.setVisibility(View.VISIBLE);
        readableText.setVisibility(View.VISIBLE);
        displayText.setVisibility(View.VISIBLE);
        menuButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyLanguageChange() {
        changeLanguage(MainActivity.this, Settings.getInstance().getLanguageID());
    }

    // lähde tälle on käyttäjän "aloj" vastaus: https://stackoverflow.com/questions/39942736/how-to-change-android-app-language-without-changing-phone-language
    private void changeLanguage(Context context, int languageID) {
        String language = settingsTranslator.getLanguageByID(languageID);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        recreate();
    }

    private void setTexts() {
        TextState ts = TextState.getInstance();

        displayText.setText(ts.getDisplayText());
        editableText.setText(ts.getEditableText());
        readableText.setText(ts.getReadableText());
    }
}