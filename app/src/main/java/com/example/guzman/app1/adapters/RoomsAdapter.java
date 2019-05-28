package com.example.guzman.app1.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guzman.app1.R;
import com.example.guzman.app1.models.RoomModel;
import com.example.guzman.app1.net.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsviewHolder> {

    Context context;
    ArrayList<RoomModel> roomModels;

    public RoomsAdapter(Context context, ArrayList<RoomModel> roomModels) {
        this.context = context;
        this.roomModels = roomModels;
    }

    @NonNull
    @Override
    public RoomsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel, parent, false);

        return new RoomsviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsviewHolder holder, int position) {

        holder.context= context;
        RoomModel roomModel = roomModels.get(position);
        holder.roomModel = roomModel;

        holder.roomName.setText(roomModel.getRoomName());
        holder.roomLikes.setText(roomModel.getRoomLikes());
        holder.roomPrice.setText(roomModel.getRoomPrice());

        String url = Urls.BASE_IMG_URL + roomModel.getImage();
        Picasso.with(context)
                .load(Uri.parse(url))
                .into(holder.roomImage);
    }

    @Override
    public int getItemCount() {
        return roomModels.size();
    }
}
