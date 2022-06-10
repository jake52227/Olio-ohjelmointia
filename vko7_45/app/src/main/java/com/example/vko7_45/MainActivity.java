// Jaakko Pyrhönen 10.6.2022
package com.example.vko7_45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.NoSuchFileException;

public class MainActivity extends AppCompatActivity {

    private EditText textBox;
    private EditText enterFilenameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textBox = findViewById(R.id.textBox);

        // lähde oheiselle: https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
        // poistaa fokuksen elementiltä, kun painetaa enter ja kutsuu closeKeyboard()
        this.textBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    textBox.clearFocus();
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });

        this.enterFilenameText = findViewById(R.id.enterFilename);
        this.enterFilenameText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    enterFilenameText.clearFocus();
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    public EditText getTextBox() {
        return this.textBox;
    }

    public EditText getEnterFilenameText() {
        return this.enterFilenameText;
    }

    // Oheinen funktio täältä: https://www.geeksforgeeks.org/how-to-programmatically-hide-android-soft-keyboard/
    // sulkee näppäimistön kutsuttaessa.
    private void closeKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // tallentaa tekstilaation sisällön käyttäjän nimeämään tiedostoon
    public void saveTextBoxContent(View v) {

        String filename = getEnterFilenameText().getText().toString();
        filename += ".txt";

        String content = this.getTextBox().getText().toString();
        try {
            OutputStreamWriter osw = new OutputStreamWriter(MainActivity.this.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(content);
            osw.close();
            this.showToast("Content written to file '"+filename+"'");
        } catch (IOException e1) {
            Log.e("IOexception", "error trying to write a file");
        } finally {
            System.out.println("tiedosto kirjoitettu");
        }
    }

    // lukee tiedoston sisällön käyttäjän tarkentamasta sijainnista ja lisää tekstiboksiin
    public void loadFromFile(View v) {
        String filename = getEnterFilenameText().getText().toString();
        filename += ".txt";

        try {
            InputStream ins = MainActivity.this.openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String fullText = "";
            String s;
            while ((s = br.readLine()) != null) {
                fullText += s;
            }
            this.getTextBox().setText(fullText);
            this.showToast("Content loaded from file '"+filename+"'");
            ins.close();
        } catch (IOException e) {
            Log.e("IOexception", "error trying to read a file");
        } finally {
            System.out.println("tiedosto luettu");
        }
    }


    // lähde toastille: https://developer.android.com/guide/topics/ui/notifiers/toasts
    private void showToast(String content) {
        Context context = getApplicationContext();
        CharSequence text = content;
        int time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }

}
