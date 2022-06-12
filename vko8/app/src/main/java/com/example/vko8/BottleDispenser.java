package com.example.vko8;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class BottleDispenser {
    private int bottles;
    private ArrayList<Bottle> bottlesArray;
    private ArrayList<Bottle> purchasesArray;
    private double money;
    private TextView dispenserScreen;


    private static BottleDispenser btlDispenser = null;

    public static BottleDispenser getInstance(TextView dispenserScreen) {
        if (btlDispenser == null) {
            btlDispenser = new BottleDispenser(dispenserScreen);
        }
        return btlDispenser;
    }

    private BottleDispenser(TextView dispenserScreen) {
        this.bottles = 5;
        this.money = 0;
        this.bottlesArray = new ArrayList<>();
        this.purchasesArray = new ArrayList<>();
        this.dispenserScreen = dispenserScreen;
        this.addDefaultBottles();
    }

    private void addDefaultBottles() {
        this.bottlesArray.add(new Bottle());
        this.bottlesArray.add(new Bottle("Pepsi Max", "Pepsi", 2.2, 1.5));
        this.bottlesArray.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 2.0, 0.5));
        this.bottlesArray.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 2.5, 1.5));
        this.bottlesArray.add(new Bottle("Fanta Zero", "Fanta", 1.95, 0.5));
        this.bottlesArray.add(new Bottle("Fanta Zero", "Fanta", 2.5, 1.5));
    }

    public void addMoney(int money) {
        this.money += money;
        this.dispenserScreen.setText("Klink! Added "+money+"€");
    }

    public void buyBottle(String bottleType, Double bottleSize) {
        if (this.bottles < 1) {
            this.dispenserScreen.setText("No bottles left");
            return;
        }

        Bottle purchase = null;
        for (Bottle btl : this.bottlesArray) {
            if ((btl.getName().equals(bottleType)) && (btl.getVolume() == bottleSize)) {
                if (btl.getPrice() > this.money) {
                    this.dispenserScreen.setText("Add money first!");
                    return;
                }
                this.money -= btl.getPrice();
                purchase = btl;
                this.purchasesArray.add(purchase);
                this.bottlesArray.remove(btl);
                break;
            }
        }
        if (purchase == null) {
            this.dispenserScreen.setText("Sorry, we ran out of that bottle");
            return;
        }

        this.dispenserScreen.setText("KACHUNK! " + purchase.getName() + " came out of the dispenser!");
    }

    public int listBottles() {
        int num = 1;
        String bottlesInfo = "Available bottles:\n";
        for (Bottle btl : this.bottlesArray) {
            bottlesInfo += num+". "+btl.getName()+", "+btl.getVolume()+"L, "+btl.getPrice()+"€\n";
            num++;
        }
        this.dispenserScreen.setText(bottlesInfo);
        return num - 1;
    }

    public void returnMoney() {
        this.dispenserScreen.setText("Klink klink. Money came out! You got "+String.format("%.2f€", this.money)+" back");
        this.money = 0;
    }

    public void generateReceipt(Context con) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(con.openFileOutput("receipt.txt", Context.MODE_PRIVATE));
            osw.write("RECEIPT\n");
            for (Bottle btl : this.purchasesArray) {
                String receiptContent = "purchase: "+btl.getName()+", size "+btl.getVolume()+"L , price: "+btl.getPrice()+"€\n";
                osw.write(receiptContent);
            }
            osw.close();
        } catch (IOException e1) {
            Log.e("IOexception", "error trying to write a file");
        } finally {
            System.out.println("file written");
        }
        this.dispenserScreen.setText("Receipt generated");
    }
}