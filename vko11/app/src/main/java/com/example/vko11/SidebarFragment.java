package com.example.vko11;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SidebarFragment extends Fragment {

    private Switch editableSwitch;
    private SideBarInterface settingsInteface;
    private SettingsTranslationInterface settingsTranslator;

    private EditText fontSize;
    private EditText displayText;

    private Spinner fontFamilySpinner;
    private Spinner textColorSpinner;
    private Spinner textStyleSpinner;
    private Spinner languageSpinner;

    private ArrayAdapter fontFamilyAdapter;
    private ArrayAdapter fontSizeAdapter;
    private ArrayAdapter textStyleAdapter;
    private ArrayAdapter languageAdapter;

    private ImageButton closeMenuButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sidebar_fragment, container, false);

        settingsTranslator = new SettingsTranslator();

        editableSwitch = (Switch) view.findViewById(R.id.allowEditingSwitch);
        fontSize = (EditText) view.findViewById(R.id.fontSizeInput);
        displayText = (EditText) view.findViewById(R.id.displayTextInput);

        fontFamilySpinner = (Spinner) view.findViewById(R.id.fontFamilySpinner);
        fontFamilyAdapter = ArrayAdapter.createFromResource(getContext(), R.array.fontFamilyArray, R.layout.spinner_style);
        fontFamilyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontFamilySpinner.setAdapter(fontFamilyAdapter);

        textColorSpinner = (Spinner) view.findViewById(R.id.textColorSpinner);
        fontSizeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.fontColorArray, R.layout.spinner_style);
        fontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textColorSpinner.setAdapter(fontSizeAdapter);

        textStyleSpinner = (Spinner) view.findViewById(R.id.textStyleSpinner);
        textStyleAdapter = ArrayAdapter.createFromResource(getContext(), R.array.styleArray, R.layout.spinner_style);
        textStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textStyleSpinner.setAdapter(textStyleAdapter);

        languageSpinner = (Spinner) view.findViewById(R.id.languageSpinner);
        languageAdapter = ArrayAdapter.createFromResource(getContext(), R.array.languageArray, R.layout.spinner_style);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        closeMenuButton = (ImageButton) view.findViewById(R.id.closeMenuButton);
        closeMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCloseMenuPress();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setSettings();
        applyChanges();
    }

    public void onCloseMenuPress() {
        settingsInteface.closeMenu();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settingsInteface = (SideBarInterface) context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        applyChanges();
        settingsInteface.notifyBasicSettingsChange();
    }

    private void applyChanges() {
        Settings st = Settings.getInstance();
        TextState ts = TextState.getInstance();

        int textSize;

        if (fontSize.getText().toString().isEmpty()) {
            textSize = 14;
        } else {
            textSize = Integer.parseInt(fontSize.getText().toString());
        }
        st.setTextFontSize(textSize);

        st.setTextIsEditable(editableSwitch.isChecked());

        int textColorIndex = textColorSpinner.getSelectedItemPosition();
        if (textColorIndex != 0) {
            st.setTextColor(settingsTranslator.getSelectedColourByIndex(textColorIndex));
        }

        String fontFamily = fontFamilySpinner.getSelectedItem().toString();
        if (!fontFamily.equals("no selection")) {
            st.setFontFamily(fontFamily);
        }

        String txtStyle = textStyleSpinner.getSelectedItem().toString();
        if (!fontFamily.equals("default")) {
            st.setTextStyle(txtStyle);
        }

        String displayTextContent = displayText.getText().toString();
        ts.setDisplayText(displayTextContent);

        int languageID = languageSpinner.getSelectedItemPosition();
        if (languageID != st.getLanguageID()) {
            st.setLanguageID(languageID);
            updateOnLanguageChange();
        }
    }

    private void updateOnLanguageChange() {
        settingsInteface.notifyLanguageChange();
    }

    // asettaa oikeat asetukset, kun näkymä luodaan. Lähinnä recreate()-kutsun takia
    private void setSettings() {
        Settings st = Settings.getInstance();
        TextState ts = TextState.getInstance();

        String[] colorsArr = getResources().getStringArray(R.array.fontColorArray);
        String[] fontsArr = getResources().getStringArray(R.array.fontFamilyArray);
        String[] textStyleArr = getResources().getStringArray(R.array.styleArray);

        for (int i = 1; i < colorsArr.length; i++) {
            if (settingsTranslator.getSelectedColourByIndex(i) == st.getTextColor()) {
                textColorSpinner.setSelection(i);
            }
        }

        for (int j = 1; j < fontsArr.length; j++) {
            if (fontsArr[j].equals(st.getFontFamily())) {
                fontFamilySpinner.setSelection(j);
            }
        }

        for (int k = 0; k < textStyleArr.length; k++) {
            if (textStyleArr[k].equals(st.getTextStyle())) {
                textStyleSpinner.setSelection(k);
            }
        }

        editableSwitch.setChecked(st.getTextIsEditable());
        fontSize.setText(String.valueOf(st.getTextFontSize()));
        languageSpinner.setSelection(st.getLanguageID());
        displayText.setText(ts.getDisplayText());
    }
}
