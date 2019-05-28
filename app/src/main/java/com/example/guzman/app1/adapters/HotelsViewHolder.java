package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
import com.example.guzman.app1.R;


public class HotelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgHotel;
    public TextView txtStars;
    public TextView txtName;
    public TextView txtDesc;
    public TextView txtLikes;
    public TextView txtLocation;

    private ItemHotelClickListener itemHotelClickListener;
    public HotelsViewHolder(View itemView) {
        super(itemView);

        imgHotel = itemView.findViewById(R.id.imgHotel);

        txtStars = itemView.findViewById(R.id.txtStars);

        txtName = itemView.findViewById(R.id.txtName);

        txtDesc = itemView.findViewById(R.id.txtDesc);

        txtLikes = itemView.findViewById(R.id.txtLikes);

        txtLocation = itemView.findViewById(R.id.txtLocation);

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




//public class HotelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    ImageView imgHotel;
//
//    TextView txtStars, txtName, txtDesc, txtLikes, txtLocation;
//
//    Context context;
//
//    HotelModel hotelModel;

//    private HotelsAdapter.onItemClickListener mListener;


//    public HotelsViewHolder(View itemView) {
//
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
//        imgHotel.setOnClickListener(this);
//
////        itemView.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////
////                if (mListener !=null){
////
////                    int position = getAdapterPosition();
////
////                    if (position !=RecyclerView.NO_POSITION){
////
////                        mListener.onItemClick(position);
////                    }
////                }
////
////            }
////        });
//
//    }

//
//    @Override
//    public void onClick(View v) {
//
//        if (v.getId()==R.id.imgHotel){
//
//            Intent intent = new Intent(context, ExploreHotel.class);
//
//            intent.putExtra("hotelname", hotelModel.getHotelName());
//
//            context.startActivity(intent);
//
//            return;
//
//        }
//
//    }
//}