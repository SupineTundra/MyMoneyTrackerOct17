package com.lofstschool.mymoneytrackeroct17;


import java.io.Serializable;

public class Item  implements Serializable{

    public final static String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";

    public int id;
    public int price;
    public String name;
    public String type;

    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

}
