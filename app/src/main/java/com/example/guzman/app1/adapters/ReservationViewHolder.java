package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
import com.example.guzman.app1.R;

public class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgHotel;
    public TextView txtHotel;
    public TextView txtRoom;
    public TextView txtPrice;
    public TextView txtArrival;
    public TextView txtDeparture;

    private ItemHotelClickListener itemHotelClickListener;

    public ReservationViewHolder(View itemView) {
        super(itemView);

        imgHotel = itemView.findViewById(R.id.imgBooking);

        txtHotel = itemView.findViewById(R.id.txtReservHotel);

        txtRoom = itemView.findViewById(R.id.txtReservRoom);

        txtPrice = itemView.findViewById(R.id.txtReservPrice);

        txtArrival = itemView.findViewById(R.id.txtArrivalDate);

        txtDeparture = itemView.findViewById(R.id.txtDepartDate);

        itemView.setOnClickListener(this);

    }

    public void setItemHotelClickListener(ItemHotelClickListener itemHotelClickListener) {
        this.itemHotelClickListener = itemHotelClickListener;
    }

    @Override
    public void onClick(View v) {

        itemHotelClickListener.onClick(v, getAdapterPosition(), false);
    }
}