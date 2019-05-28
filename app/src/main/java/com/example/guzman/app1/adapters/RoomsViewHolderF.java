package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemCategoryClickListener;
import com.example.guzman.app1.R;

public class RoomsViewHolderF extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView Name, Price;
   public ImageView Image;

    private ItemCategoryClickListener itemCategoryClickListener;

    public RoomsViewHolderF(View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.txtNameRoom);
        Price = itemView.findViewById(R.id.txtPriceRoom);
        Image = itemView.findViewById(R.id.imgRoomImage);

        itemView.setOnClickListener(this);
    }

    public void setItemCategoryClickListener(ItemCategoryClickListener itemCategoryClickListener) {
        this.itemCategoryClickListener = itemCategoryClickListener;
    }

    @Override
    public void onClick(View v) {
        itemCategoryClickListener.onClick(v, getAdapterPosition(), false);

    }
}
