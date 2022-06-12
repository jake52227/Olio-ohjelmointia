package com.example.vko8;


public class Bottle {
    private String name;
    private String manufacturer;
    private double price;
    private double volume;

    public Bottle() {
        this.manufacturer = "Pepsi";
        this.name = "Pepsi Max";
        this.price = 1.8;
        this.volume = 0.5;
    }

    public Bottle(String name, String manuf, double price, double volume) {
        this.name = name;
        this.manufacturer = manuf;
        this.price = price;
        this.volume = volume;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public double getPrice() {
        return this.price;
    }

    public double getVolume() {
        return this.volume;
    }
}