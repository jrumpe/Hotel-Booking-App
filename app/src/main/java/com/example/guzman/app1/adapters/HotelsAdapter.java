package com.example.guzman.app1.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guzman.app1.R;
import com.example.guzman.app1.models.HotelModel;
import com.example.guzman.app1.net.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//public class HotelsAdapter extends RecyclerView.Adapter<HotelsViewHolder> {
//    Context context;
//
//    ArrayList<HotelModel> hotelModels;
//
//    private onItemClickListener mListener;
//
//    public interface onItemClickListener {
//
//        void onItemClick(int position);
//
//    }
//
//    public void setOnItemClickListener (onItemClickListener listener){
//
//        mListener = listener;
//
//    }

//    public HotelsAdapter(Context context, ArrayList<HotelModel> hotelModels) {
//
//        this.context = context;
//
//        this.hotelModels = hotelModels;
//    }

//    @NonNull
//    @Override
//    public HotelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater
//
//                .from(parent.getContext())
//
//                .inflate(R.layout.hotel_tonight_sample, parent, false);
//
//        return new HotelsViewHolder(view);
//    }

    //@Override
//    public void onBindViewHolder(@NonNull HotelsViewHolder holder, int position) {
//
//        holder.context = context;
//
//        HotelModel hotelModel = hotelModels.get(position);
//
//        holder.hotelModel = hotelModel;
//
//        holder.txtStars.setText(hotelModel.getHotelStars());
//
//        holder.txtName.setText(hotelModel.getHotelName());
//
//        holder.txtDesc.setText(hotelModel.getHotelDesc());
//
//        holder.txtLikes.setText(hotelModel.getHotelLikes());
//
//        holder.txtLocation.setText(hotelModel.getHotelLocation());
//
////        holder.imageLike.setTag(hotelModel.getLikes());
//
//        String url = Urls.BASE_IMG_URL + hotelModel.getImage();
//
//        Picasso.with(context)
//
//                .load(Uri.parse(url))
//
//                .into(holder.imgHotel);
//        }

//    @Override
//    public int getItemCount() {
//
//        return hotelModels.size();
//    }
