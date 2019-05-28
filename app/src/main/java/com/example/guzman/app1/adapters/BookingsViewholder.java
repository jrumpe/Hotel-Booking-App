package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemClickListener;
import com.example.guzman.app1.R;

public class BookingsViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtNameGuest;
    public TextView txtRoom;
    public TextView txtAmount;
    public TextView bookarrival;
    public TextView bookdeparture;
    public TextView bookstatus;

    private ItemClickListener itemClickListener;

    public BookingsViewholder(View itemView) {
        super(itemView);

        txtNameGuest = (TextView) itemView.findViewById(R.id.users_name);
        txtRoom = (TextView) itemView.findViewById(R.id.booked_room);
        txtAmount = (TextView) itemView.findViewById(R.id.book_amount);
        bookarrival = (TextView) itemView.findViewById(R.id.book_arrival);
        bookdeparture = (TextView) itemView.findViewById(R.id.book_departure);
        bookstatus = (TextView) itemView.findViewById(R.id.book_status);

        itemView.setOnClickListener(this);

    }

    public void setTxtNameGuest(TextView txtNameGuest) {
        this.txtNameGuest = txtNameGuest;
    }

    public void setTxtRoom(TextView txtRoom) {
        this.txtRoom = txtRoom;
    }

    public void setTxtAmount(TextView txtAmount) {
        this.txtAmount = txtAmount;
    }

    public void setBookarrival(TextView bookarrival) {
        this.bookarrival = bookarrival;
    }

    public void setBookdeparture(TextView bookdeparture) {
        this.bookdeparture = bookdeparture;
    }

    public void setBookstatus(TextView bookstatus) {
        this.bookstatus = bookstatus;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false );

    }
}
