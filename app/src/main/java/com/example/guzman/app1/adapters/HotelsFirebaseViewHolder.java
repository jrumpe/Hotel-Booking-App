//package com.example.guzman.app1.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.guzman.app1.ExploreHotel;
//import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
//import com.example.guzman.app1.R;
//import com.example.guzman.app1.models.HotelsFirebaseModel;
//
//public class HotelsFirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    HotelsFirebaseModel hotelsFirebaseModel;
//
//    Context context;
//
//    public ImageView imgHotel;
//    public TextView txtStars;
//    public TextView txtName;
//    public TextView txtDesc;
//    public TextView txtLikes;
//    public TextView txtLocation;
//
//    public HotelsFirebaseViewHolder(View itemView) {
//        super(itemView);
//
//        imgHotel = itemView.findViewById(R.id.imgHotel);
//
//        txtStars = itemView.findViewById(R.id.txtStars);
//
//        txtName = itemView.findViewById(R.id.txtName);
//
//        txtDesc = itemView.findViewById(R.id.txtDesc);
//
//        txtLikes = itemView.findViewById(R.id.txtLikes);
//
//        txtLocation = itemView.findViewById(R.id.txtLocation);
//
//        itemView.setOnClickListener(this);
//
//    }
//
//
//    public void setHotelsFirebaseModel(HotelsFirebaseModel hotelsFirebaseModel) {
//        this.hotelsFirebaseModel = hotelsFirebaseModel;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    public void setImgHotel(ImageView imgHotel) {
//        this.imgHotel = imgHotel;
//    }
//
//    public void setTxtStars(TextView txtStars) {
//        this.txtStars = txtStars;
//    }
//
//    public void setTxtName(TextView txtName) {
//        this.txtName = txtName;
//    }
//
//    public void setTxtDesc(TextView txtDesc) {
//        this.txtDesc = txtDesc;
//    }
//
//    public void setTxtLikes(TextView txtLikes) {
//        this.txtLikes = txtLikes;
//    }
//
//    public void setTxtLocation(TextView txtLocation) {
//        this.txtLocation = txtLocation;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        ItemHotelClickListener.onClick(v, getAdapterPosition(), false);
//
//        }
//    }
//
