package com.lp.brandon.poetrycrud.data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.lp.brandon.poetrycrud.Models.Dish;

import nl.elastique.poetry.database.DatabaseConfiguration;

/**
 * Created by brand on 23/04/2017.
 */

public class DatabaseHelper extends nl.elastique.poetry.database.DatabaseHelper {

    private static final int DB_VERSION = 1;

    public static final DatabaseConfiguration CONFIGURATION = new DatabaseConfiguration(DB_VERSION, new Class<?>[]{
            Dish.class
    });

    public DatabaseHelper(Context context) {
        super(context, CONFIGURATION);
    }

    public static DatabaseHelper getHelper(Context context){
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
