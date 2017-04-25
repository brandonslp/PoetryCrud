package com.lp.brandon.poetrycrud.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import nl.elastique.poetry.json.annotations.MapFrom;

/**
 * Created by brand on 23/04/2017.
 */
@DatabaseTable
public class Dish {

    @DatabaseField(columnName = "id",generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    @MapFrom("name")
    private String name;
    @DatabaseField(columnName = "description")
    @MapFrom("description")
    private String description;
    @DatabaseField(columnName = "dishType")
    @MapFrom("dishType")
    private String dishType;
    @DatabaseField(columnName = "foodType")
    @MapFrom("foodType")
    private String foodType;
    @DatabaseField(columnName = "price")
    @MapFrom("price")
    private double price;

    public Dish() {
    }

    public Dish(String name, String description, String dishType, String foodType, double price) {
        this.name = name;
        this.description = description;
        this.dishType = dishType;
        this.foodType = foodType;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
