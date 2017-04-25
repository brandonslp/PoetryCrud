package com.lp.brandon.poetrycrud.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.lp.brandon.poetrycrud.models.Dish;
import com.lp.brandon.poetrycrud.R;
import com.lp.brandon.poetrycrud.controllers.Dishes;

import java.sql.SQLException;

public class DishForm extends AppCompatActivity {
    private EditText edtName, edtDescriprion, edtPrice;
    private Spinner spnDishTypes, spnFoodTypes;
    private Button btnAction;
    private Dishes controllerDishes;
    private Dish dish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Log.v("Brandon-lp","Llega -> "+getIntent().getIntExtra("dishId",-1));
        initilization();
        setListener();
        if (update()){
            dish = controllerDishes.getById(
                    getIntent().getIntExtra("dishId",-1)
            );
            loadData(dish);
            btnAction.setText(getResources().getString(R.string.btnDishFormUpdate));
        }
    }

    private boolean update(){
        if (getIntent().getIntExtra("dishId",-1)!=-1)
            return true;
        return false;
    }

    private void initilization() {
        try {
            controllerDishes = new Dishes(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        edtName = (EditText) findViewById(R.id.edtDishName);
        edtDescriprion = (EditText) findViewById(R.id.edtDishDescription);
        edtPrice = (EditText) findViewById(R.id.edtDishPrice);

        spnDishTypes = (Spinner) findViewById(R.id.spnDishType);
        spnFoodTypes = (Spinner) findViewById(R.id.spnDishFoodType);

        btnAction = (Button) findViewById(R.id.btndishform);

    }

    private void loadData(Dish dish){
        edtName.setText(dish.getName());
        edtDescriprion.setText(dish.getDescription());
        edtPrice.setText(String.valueOf(dish.getPrice()));
        spnDishTypes.setSelection(getTypePosition(
                getResources().getStringArray(R.array.dishTypes),dish.getDishType()
        ));
        spnFoodTypes.setSelection(getTypePosition(
                getResources().getStringArray(R.array.foodTypes),dish.getFoodType()
        ));
    }

    private int getTypePosition(String types[], String type){
        for (int i=0; i<types.length; i++){
            if (types[i].equalsIgnoreCase(type))
                return i;
        }
        return -1;
    }

    private void setListener(){

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!update())
                    addDish();
                else
                    updateDish(dish);
                finish();
            }
        });

    }

    private void addDish(){
        Dish dish = new Dish(
          edtName.getText().toString(),
          edtDescriprion.getText().toString(),
          spnDishTypes.getSelectedItem().toString(),
          spnFoodTypes.getSelectedItem().toString(),
          Double.parseDouble(edtPrice.getText().toString())
        );
        boolean result = controllerDishes.addDish(dish);
        Log.v("Brandon-lp", "Insercion -> "+result);
    }

    private void updateDish(Dish dish){
        dish.setName(edtName.getText().toString());
        dish.setDescription(edtDescriprion.getText().toString());
        dish.setDishType(spnDishTypes.getSelectedItem().toString());
        dish.setFoodType(spnFoodTypes.getSelectedItem().toString());
        dish.setPrice(Double.parseDouble(edtPrice.getText().toString()));
        Log.v("Brandon-lp","update -> "+controllerDishes.update(dish));
    }

}
