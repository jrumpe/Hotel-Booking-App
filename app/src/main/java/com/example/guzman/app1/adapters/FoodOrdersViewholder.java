package com.example.guzman.app1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemOrderClickListener;
import com.example.guzman.app1.R;

public class FoodOrdersViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtUsersPhone;
    public TextView txtUsersEmail;
    public TextView txtfoodName;
    public TextView txtAmount;
    public TextView bookstatus;

    private ItemOrderClickListener  itemOrderClickListener;

    public FoodOrdersViewholder(View itemView) {
        super(itemView);
        txtUsersEmail = (TextView) itemView.findViewById(R.id.users_email);
        txtUsersPhone = (TextView) itemView.findViewById(R.id.users_phone);
        txtfoodName = (TextView) itemView.findViewById(R.id.food_name);
        txtAmount = (TextView) itemView.findViewById(R.id.amount);
        bookstatus = (TextView) itemView.findViewById(R.id.payment_state);

        itemView.setOnClickListener(this);

    }

    public void setItemOrderClickListener(ItemOrderClickListener itemOrderClickListener) {
        this.itemOrderClickListener = itemOrderClickListener;
    }

    @Override
    public void onClick(View v) {
        
        itemOrderClickListener.onClick(v, getAdapterPosition(), false );

    }
}
