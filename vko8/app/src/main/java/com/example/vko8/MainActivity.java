// Jaakko Pyrhönen 12.6.2022
package com.example.vko8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView dispenserScreen;
    private TextView moneyBarValue;
    private SeekBar moneybar;
    private Button moneyBarAddButton;
    private Spinner bottleOptions;
    private Spinner bottleSizeOptions;

    private BottleDispenser bottleDispenser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.moneyBarAddButton = (Button) findViewById(R.id.moneyBarAdd);
        this.moneyBarValue = (TextView) findViewById(R.id.moneyBarValue);
        this.dispenserScreen = (TextView) findViewById(R.id.dispenserScreen);
        this.bottleOptions = (Spinner) findViewById(R.id.bottleOptions);
        this.bottleSizeOptions = (Spinner) findViewById(R.id.bottleSizeOptions);
        this.bottleDispenser = BottleDispenser.getInstance(this.dispenserScreen);

        // spinnerien konfigurointi
        ArrayAdapter<CharSequence> bottleOptionsAdapter = ArrayAdapter.createFromResource(this, R.array.bottlesArray, android.R.layout.simple_spinner_item);
        bottleOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bottleOptions.setAdapter(bottleOptionsAdapter);
        ArrayAdapter<CharSequence> bottleSizeOptionsAdapter = ArrayAdapter.createFromResource(this, R.array.bottleSizesArray, android.R.layout.simple_spinner_item);
        bottleSizeOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bottleSizeOptions.setAdapter(bottleSizeOptionsAdapter);

        // SeekBar:n konfigurointi
        this.moneybar = (SeekBar) findViewById(R.id.moneyBar);
        moneybar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double barValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Integer val = i;
                moneyBarValue.setText(val.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    public void onMoneyAdd(View v) {
        int money = Integer.parseInt(moneyBarValue.getText().toString());
        this.bottleDispenser.addMoney(money);
        this.moneybar.setProgress(0);
    }

    public void onBottlePurchase(View v) {
        String bottleType = this.bottleOptions.getSelectedItem().toString();
        Double bottleSize = Double.parseDouble(this.bottleSizeOptions.getSelectedItem().toString());
        this.bottleDispenser.buyBottle(bottleType, bottleSize);
    }

    public void onCashReturn(View v) {
        this.bottleDispenser.returnMoney();
    }

    public void onListBottles(View v) {
        this.bottleDispenser.listBottles();
    }

    public void onGetReceipt(View v) {
        this.bottleDispenser.generateReceipt(MainActivity.this);
    }
}
