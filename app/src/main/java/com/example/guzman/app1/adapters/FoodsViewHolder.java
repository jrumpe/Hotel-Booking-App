package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemFoodClickListener;
import com.example.guzman.app1.R;

public class FoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Name;
    public ImageView imgFoodImage;
    public TextView likes, Price;
    private ItemFoodClickListener itemFoodClickListener;

    public FoodsViewHolder(View itemView) {
        super(itemView);

        Name = itemView.findViewById(R.id.txtNameFood);
        likes = itemView.findViewById(R.id.txtLikes);
        Price = itemView.findViewById(R.id.txtPrice);
        imgFoodImage = itemView.findViewById(R.id.imgFoodItem);

        itemView.setOnClickListener(this);
    }

    public void setItemFoodClickListener(ItemFoodClickListener itemFoodClickListener) {
        this.itemFoodClickListener = itemFoodClickListener;
    }

    @Override
    public void onClick(View v) {
        itemFoodClickListener.onClick(v,getAdapterPosition(),false);

    }
}
