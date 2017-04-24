package com.lp.brandon.poetrycrud.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.lp.brandon.poetrycrud.Models.Dish;
import com.lp.brandon.poetrycrud.data.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by brand on 23/04/2017.
 */

public class Dishes {
    private final DatabaseHelper helper;
    private Dao<Dish, Integer> dishDao;

    public Dishes(Context context) throws SQLException {
        this.helper = DatabaseHelper.getHelper(context);
        this.dishDao = this.helper.getDao(Dish.class);
    }

    public boolean addDish(Dish dish){
        try {
           return dishDao.createOrUpdate(dish).isCreated();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int update(Dish dish){
        try {
            return dishDao.update(dish);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int destroy(Dish dish){
        try {
            return dishDao.delete(dish);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Dish> getAll(){
        try {
            return dishDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Dish getById(int id){
        try {
            return dishDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Dish> DishesByFoodType(String foodType){
        try {
           return dishDao.queryForEq("foodType", foodType);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
