package com.lp.brandon.poetrycrud.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lp.brandon.poetrycrud.Acitivities.DishForm;
import com.lp.brandon.poetrycrud.Acitivities.MainActivity;
import com.lp.brandon.poetrycrud.Models.Dish;
import com.lp.brandon.poetrycrud.R;

import java.util.List;

/**
 * Created by brand on 23/04/2017.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishes;
    private Context context;

    public DishAdapter(List<Dish> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        Dish  dish = dishes.get(position);
        holder.position = position;
        holder.name.setText(dish.getName());
        holder.description.setText(dish.getDescription());
        holder.price.setText(String.valueOf(dish.getPrice()));
        holder.dishtype.setText(dish.getDishType());
        holder.foodType.setText(dish.getFoodType());
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView name, description, dishtype, foodType, price;
        CardView cv;
        int position;
        public DishViewHolder(View item){
            super(item);
            cv = (CardView) item.findViewById(R.id.itemcv);
            name = (TextView) item.findViewById(R.id.itemName);
            description = (TextView) item.findViewById(R.id.itemDescription);
            dishtype = (TextView) item.findViewById(R.id.itemDishType);
            foodType = (TextView) item.findViewById(R.id.itemFoodType);
            price = (TextView) item.findViewById(R.id.itemPrice);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PopupMenu menu = new PopupMenu(v.getContext(),v);
            menu.inflate(R.menu.dish_item_menu);
            menu.setOnMenuItemClickListener(this);
            menu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.itemDishMenuEdit:
                    Log.v("Brandon-lp", "Edit Menu -> "+dishes.get(position).getId());
                    Intent i = new Intent(context, DishForm.class);
                    i.putExtra("dishId", dishes.get(position).getId());
                    context.startActivity(i);
                    break;
                case R.id.itemDishMenuDelete:
                    Log.v("Brandon-lp", "Delete");
                    int result = ((MainActivity)context)
                            .getDishController()
                            .destroy(
                               dishes.get(position)
                            );
                    Log.v("Brandon-lp","delete result -> "+result);
                    if (result == 1)
                        cv.setVisibility(View.GONE);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

}

