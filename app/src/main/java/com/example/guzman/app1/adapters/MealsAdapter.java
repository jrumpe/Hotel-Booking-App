//package com.example.guzman.app1.adapters;
//
//import android.content.Context;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.guzman.app1.R;
//import com.example.guzman.app1.models.MealModel;
//import com.example.guzman.app1.net.Urls;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class MealsAdapter extends RecyclerView.Adapter<MealsViewHolder> {
//    Context context;
//    ArrayList<MealModel> mealModels;
//
//    public MealsAdapter(Context context, ArrayList<MealModel> mealModels) {
//        this.context = context;
//        this.mealModels = mealModels;
//    }
//
//    @NonNull
//    @Override
//    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food, parent, false);
//
//        return new MealsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
//        holder.context= context;
//        MealModel mealModel =mealModels.get(position);
//        holder.mealModel= mealModel;
//
//        holder.foodName.setText(mealModel.getFoodName());
//        holder.foodLikes.setText(mealModel.getFoodLikes());
//        holder.foodPrice.setText(mealModel.getFoodPrice());
//
//        String url = Urls.BASE_FOOD_IMG_URL + mealModel.getImage();
//        Picasso.with(context)
//                .load(Uri.parse(url))
//                .into(holder.foodImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mealModels.size();
//    }
//}
