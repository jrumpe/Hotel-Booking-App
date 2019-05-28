package com.example.guzman.app1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.guzman.app1.Intrerface.ItemClickListener;
import com.example.guzman.app1.adapters.BookingsViewholder;
import com.example.guzman.app1.adapters.RoomsviewHolder;
import com.example.guzman.app1.models.BookingsModel;
import com.example.guzman.app1.models.RoomModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

public class BookedRooms extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference bookings;

    RecyclerView recycler_bookings;
    RecyclerView.LayoutManager laytManager;
    ConstraintLayout consLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_rooms);

        consLayout = (ConstraintLayout)findViewById(R.id.layout_booked_rooms);

        if (!isDeviceOnline())
            Snackbar.make(consLayout, "No network connection available.", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();

        database =FirebaseDatabase.getInstance();
        bookings= database.getReference("Bookings");

        recycler_bookings = (RecyclerView) findViewById(R.id.recycler_bookings);
        recycler_bookings.setHasFixedSize(true);
        laytManager = new LinearLayoutManager(this);
        recycler_bookings.setLayoutManager(laytManager);

        loadBookings();
    }
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void loadBookings() {

     FirebaseRecyclerAdapter<BookingsModel,BookingsViewholder> adapter = new FirebaseRecyclerAdapter<BookingsModel, BookingsViewholder>(BookingsModel.class,R.layout.booked_item,BookingsViewholder.class,bookings  ) {
         @Override
         protected void populateViewHolder(BookingsViewholder viewHolder, final BookingsModel model, int position) {

             viewHolder.txtNameGuest.setText(model.getName());
             viewHolder.txtRoom.setText(model.getRoom());
             viewHolder.txtAmount.setText(model.getAmount());
             viewHolder.bookarrival.setText(model.getArrival());
             viewHolder.bookdeparture.setText(model.getDeparture());
             viewHolder.bookstatus.setText(model.getStatus());

             final BookingsModel clickItem = model;

             viewHolder.setItemClickListener(new ItemClickListener() {
                 @Override
                 public void onClick(View view, int position, boolean isLongClick) {

                     TastyToast.makeText(BookedRooms.this, "" + clickItem.getName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                 }
             });


         }
     };
     recycler_bookings.setAdapter(adapter);

    }
}
