package com.example.guzman.app1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.guzman.app1.Intrerface.ItemOrderClickListener;
import com.example.guzman.app1.adapters.FoodOrdersViewholder;
import com.example.guzman.app1.models.FoodOrdersModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

public class Orders extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference orders;

    RecyclerView recycler_orders;
    RecyclerView.LayoutManager laytManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);

        database =FirebaseDatabase.getInstance();
        orders= database.getReference("Orders");

        recycler_orders = (RecyclerView) findViewById(R.id.recycler_orders);
        recycler_orders.setHasFixedSize(true);
        laytManager= new LinearLayoutManager( this);
        recycler_orders.setLayoutManager(laytManager);

        loadOrders();

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

    private void loadOrders() {

        FirebaseRecyclerAdapter<FoodOrdersModel,FoodOrdersViewholder> adapter = new FirebaseRecyclerAdapter<FoodOrdersModel, FoodOrdersViewholder>(FoodOrdersModel.class,R.layout.ordered_item,FoodOrdersViewholder.class,orders  ) {
            @Override
            protected void populateViewHolder(FoodOrdersViewholder viewHolder, final FoodOrdersModel model, int position) {

                viewHolder.txtUsersEmail.setText(model.getUseremail());
                viewHolder.txtUsersPhone.setText(model.getUserphone());
                viewHolder.txtAmount.setText(model.getAmount());
                viewHolder.txtfoodName.setText(model.getFoodname());
                viewHolder.bookstatus.setText(model.getStatus());


                final FoodOrdersModel clickItem = model;

                viewHolder.setItemOrderClickListener(new ItemOrderClickListener() {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        TastyToast.makeText(Orders.this, "" + clickItem.getFoodname(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                    }
                });


            }
        };
        recycler_orders.setAdapter(adapter);
    }

}
