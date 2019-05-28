package com.example.guzman.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.guzman.app1.Intrerface.ItemFoodClickListener;
import com.example.guzman.app1.adapters.FoodsViewHolder;
import com.example.guzman.app1.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

public class FoodList extends AppCompatActivity {
    String CategoryId;

    FirebaseRecyclerAdapter<Food, FoodsViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference foods;

    RecyclerView recyclerFoods;
    SwipeRefreshLayout swipeFoods;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        db = FirebaseDatabase.getInstance();
        foods = db.getReference("Foods");

        recyclerFoods = findViewById(R.id.recycler_foods);
        recyclerFoods.setItemAnimator(new DefaultItemAnimator());
        recyclerFoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        pbar = findViewById(R.id.pbar);

        swipeFoods = findViewById(R.id.swipeRefresh);

        if (getIntent() !=null)
            CategoryId = getIntent().getStringExtra("CategoryId");

        if (!CategoryId.isEmpty() && CategoryId !=null){
            LoadListFoods(CategoryId);
        }
        swipeFoods.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadListFoods(CategoryId);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void LoadListFoods(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodsViewHolder>(Food.class,
                R.layout.layout_food_item,
                FoodsViewHolder.class,
                //select from Foods * where MenuId= category id
                foods.orderByChild("MenuId").equalTo(CategoryId)) {
            @Override
            protected void populateViewHolder(FoodsViewHolder viewHolder, Food model, int position) {
                viewHolder.Name.setText(model.getName());
                viewHolder.Price.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgFoodImage);

                final Food local = model;

                viewHolder.setItemFoodClickListener(new ItemFoodClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

//                        Intent intent = new Intent(FoodList.this, OrderFood.class);
//
//                        startActivity(intent);
                        TastyToast.makeText(FoodList.this, "" + local.getName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                    }
                });
            }
        };
        recyclerFoods.setAdapter(adapter);
    }

//    private void LoadFoods() {
//
//        adapter = new FirebaseRecyclerAdapter<Food, FoodsViewHolder>(Food.class, R.layout.food, FoodsViewHolder.class, foods) {
//            @Override
//            protected void populateViewHolder(FoodsViewHolder viewHolder, Food model, int position) {
//                viewHolder.Name.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgFoodImage);
//
//                final Food clickItem = model;
//
//                viewHolder.setItemFoodClickListener(new ItemFoodClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//
//                    }
//                });
//            }
//        };
//    }

}
