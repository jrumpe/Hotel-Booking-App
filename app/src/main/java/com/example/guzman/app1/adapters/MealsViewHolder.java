package com.example.guzman.app1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guzman.app1.ExploreHotel;
import com.example.guzman.app1.Intrerface.ItemCategoryClickListener;
import com.example.guzman.app1.OrderFood;
import com.example.guzman.app1.R;
import com.example.guzman.app1.models.MealModel;

public class MealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView foodName, foodLikes, foodPrice;
    ImageView foodImage;

    public TextView Name;
    public ImageView imgImage;
    private ItemCategoryClickListener itemCategoryClickListener;



    public MealsViewHolder(View itemView) {
        super(itemView);

        Name = itemView.findViewById(R.id.txtName);
        imgImage = itemView.findViewById(R.id.imgCategory);

        itemView.setOnClickListener(this);

//        foodName = itemView.findViewById(R.id.txtName);
//        foodLikes = itemView.findViewById(R.id.txtLikes);
//        foodPrice = itemView.findViewById(R.id.txtPrice);
//
//        foodImage = itemView.findViewById(R.id.imgFood);
//
//        foodImage.setOnClickListener(this);



    }

    public void setItemCategoryClickListener(ItemCategoryClickListener itemCategoryClickListener) {
        this.itemCategoryClickListener = itemCategoryClickListener;
    }

    @Override
    public void onClick(View v) {

        itemCategoryClickListener.onClick(v, getAdapterPosition(), false);


//        if (v.getId()==R.id.imgFood){
//
//            Intent intent = new Intent(context, OrderFood.class);
//
//            intent.putExtra("foodname", mealModel.getFoodName());
//            intent.putExtra("foodprice", mealModel.getFoodPrice());
//            intent.putExtra("foodimage", mealModel.getImage());
//
//            context.startActivity(intent);
//
//            return;
//
//        }
      //  context.startActivity(new Intent(context, OrderFood.class));
    }
}
