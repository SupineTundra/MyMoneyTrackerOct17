package com.lofstschool.mymoneytrackeroct17;


public class Item {

    public final static String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";

    public int id;
    public int price;
    public String name;
    public String type;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

}
