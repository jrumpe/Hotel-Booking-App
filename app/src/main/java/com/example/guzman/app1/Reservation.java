package com.example.guzman.app1;

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

import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
import com.example.guzman.app1.adapters.ReservationViewHolder;
import com.example.guzman.app1.models.ReservationModel;
import com.example.guzman.app1.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

public class Reservation extends AppCompatActivity {
    String UserId;

    FirebaseRecyclerAdapter<ReservationModel, ReservationViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference bookings;

    RecyclerView recyclerReerv;
    SwipeRefreshLayout swipeReserv;
    ProgressBar pbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        UserId=mAuth.getUid();
        db = FirebaseDatabase.getInstance();
        bookings = db.getReference("Bookings");

        recyclerReerv = findViewById(R.id.recycler_reserv);
//        recyclerReerv.setItemAnimator(new DefaultItemAnimator());
        recyclerReerv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        pbar = findViewById(R.id.pbar);

        swipeReserv = findViewById(R.id.swipeRefresh);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadReservations(UserId);

        swipeReserv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadReservations(UserId);
            }
        });
    }


    private void LoadReservations(String UserId) {

        adapter = new FirebaseRecyclerAdapter<ReservationModel, ReservationViewHolder>(ReservationModel.class,
                R.layout.layout_card_reservation,
                ReservationViewHolder.class,
                //select from Foods * where MenuId= category id
                bookings.orderByChild("BookingsId")) {
            @Override
            protected void populateViewHolder(ReservationViewHolder viewHolder, ReservationModel model, int position) {
                //viewHolder.txtHotel.setText(model.getHotel());
                viewHolder.txtRoom.setText(model.getRoom());
                viewHolder.txtPrice.setText(model.getPrice());
                viewHolder.txtArrival.setText(model.getArrival());
                viewHolder.txtDeparture.setText(model.getDeparture());
                //Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgHotel);

                final ReservationModel local = model;
                viewHolder.setItemHotelClickListener(new ItemHotelClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        TastyToast.makeText(Reservation.this, "" + local.getHotel(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                    }
                });
            }
        };
        recyclerReerv.setAdapter(adapter);
    }

}
