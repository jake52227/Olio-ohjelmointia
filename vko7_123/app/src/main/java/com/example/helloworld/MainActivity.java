package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private TextView sayHelloText;
    private EditText textInputBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sayHelloText = (TextView) findViewById(R.id.sayHello);
        this.textInputBox = (EditText) findViewById(R.id.textInputEditText);
    }

    public TextView getSayhelloText() {
        return this.sayHelloText;
    }

    public EditText getTextInputBox() {
        return this.textInputBox;
    }

    public void helloWorld(View v) {
        System.out.println("Hello World!");
        this.getSayhelloText().setText("Hello World!");
    }

    public void updateText(View v) {
        String textOnBox = this.getTextInputBox().getText().toString();
        this.getTextInputBox().clearFocus();
        this.getTextInputBox().setText(textOnBox);
    }
}