package com.lp.brandon.poetrycrud.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.lp.brandon.poetrycrud.models.Dish;
import com.lp.brandon.poetrycrud.R;
import com.lp.brandon.poetrycrud.adapters.DishAdapter;
import com.lp.brandon.poetrycrud.controllers.Dishes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private Dishes dishController;
    private DishAdapter dishAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intialization();
        setListeners();
        //handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //setIntent(intent);
        //handleIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList(dishController.getAll());
        //handleIntent(getIntent());
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v("Brandon-lp","search -> "+query);
            if (!query.isEmpty())
                filterList(query);
        }
    }

    private void filterList(String name){
        List<Dish> filter = new ArrayList<>();
        List<Dish> dishAll = dishController.getAll();
        for (Dish d: dishAll) {
            Log.v("Brandon-lp","match -> "+d.getName().equalsIgnoreCase(name));
            if (d.getName().toLowerCase().contains(name.toLowerCase()))
                filter.add(d);
        }
        if (!filter.isEmpty()){
            Log.v("Brandon-lp","filter -> "+filter.get(0).getName().equalsIgnoreCase(name));
            updateList(filter);
        }else updateList(dishAll);
    }


    private void intialization() {
        try {
            dishController = new Dishes(this);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            recyclerView = (RecyclerView) findViewById(R.id.dishRecyclerView);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            Log.v("Brandon-lp","si lo trae -> "+dishController.getAll().get(0).getName());
            updateList(dishController.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInsertOrUpdate(0);
            }
        });
    }

    private void toInsertOrUpdate(int action){
        Intent intent = new Intent(this,DishForm.class);
        switch (action){
            case 0:
                startActivity(intent);
                break;
            case 1:
                break;
        }
    }

    private void updateList(List<Dish> list){
        dishAdapter = new DishAdapter(list,this);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.notifyDataSetChanged();
        //Log.v("Brandon-lp","Desde el filter list -> "+list.get(0).getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.searchable_menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
            Log.v("Brandon-lp","item null");
        }
        if (searchView != null){
            Log.v("Brandon-lp","view null");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.v("Brandon-lp","submit -> "+query);
                    filterList(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.equals("")){
                        this.onQueryTextSubmit("");
                    }
                    return true;
                }
            });
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_main_filter:
                Intent intent = new Intent(this,Filter.class);
                startActivity(intent);
                break;
            case R.id.menu_main_about:
                showdialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showdialog(){
        new SweetAlertDialog(this)
                .setTitleText("Acerca de")
                .setContentText("Antonio Barandica\nBrandon López")
                .show();
    }

    public Dishes getDishController() {
        return dishController;
    }
}
