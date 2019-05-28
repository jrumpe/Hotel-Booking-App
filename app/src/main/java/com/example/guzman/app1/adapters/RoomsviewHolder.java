package com.example.guzman.app1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guzman.app1.BookRoom;
import com.example.guzman.app1.ExploreHotel;
import com.example.guzman.app1.R;
import com.example.guzman.app1.models.RoomModel;

public class RoomsviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView roomName;
    TextView roomPrice;
    TextView roomLikes;

    ImageView roomImage;

    Context context;
    RoomModel roomModel;

    public RoomsviewHolder(View itemView) {
        super(itemView);
        roomName = itemView.findViewById(R.id.txtName);
        roomLikes = itemView.findViewById(R.id.txtLikes);
        roomPrice = itemView.findViewById(R.id.txtPrice);
        roomImage = itemView.findViewById(R.id.imgRoom);

        roomImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        context.startActivity(new Intent(context, BookRoom.class));
    }
}
