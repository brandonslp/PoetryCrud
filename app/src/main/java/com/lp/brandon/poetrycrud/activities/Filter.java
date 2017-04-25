package com.lp.brandon.poetrycrud.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.lp.brandon.poetrycrud.R;
import com.lp.brandon.poetrycrud.adapters.DishAdapter;
import com.lp.brandon.poetrycrud.controllers.Dishes;
import com.lp.brandon.poetrycrud.models.Dish;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Filter extends AppCompatActivity {
    private Spinner spnFilter;
    private RecyclerView recyclerView;
    private Dishes dishController;
    private DishAdapter dishAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initialization();
        listeners();
    }

    private void initialization(){
        try {
            dishController = new Dishes(this);
            recyclerView = (RecyclerView) findViewById(R.id.dishRecyclerView);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            updateList(dishController.getAll());
            spnFilter = (Spinner) findViewById(R.id.filterspn);
            filterList(getResources().getStringArray(R.array.foodTypes)[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void listeners(){
        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Brandon-lp","Selecciono "+getResources().getStringArray(R.array.foodTypes)[position]);
                filterList(getResources().getStringArray(R.array.foodTypes)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateList(List<Dish> list){
        dishAdapter = new DishAdapter(list,this);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.notifyDataSetChanged();
        //Log.v("Brandon-lp","Desde el filter list -> "+list.get(0).getName());
    }
    private void filterList(String name){
        List<Dish> filter = new ArrayList<>();
        List<Dish> dishAll = dishController.getAll();
        for (Dish d: dishAll) {
            Log.v("Brandon-lp","match -> "+d.getFoodType().equalsIgnoreCase(name));
            if (d.getFoodType().toLowerCase().contains(name.toLowerCase()))
                filter.add(d);
        }
        if (!filter.isEmpty()){
            Log.v("Brandon-lp","filter -> "+filter.get(0).getFoodType().equalsIgnoreCase(name));
            updateList(filter);
        }else updateList(dishAll);
    }
}
