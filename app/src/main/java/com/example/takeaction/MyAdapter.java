package com.example.takeaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context context;
    ArrayList<Incident> models;  //this array is create a list of array which parametres define in our incident class



    public MyAdapter(Context context, ArrayList<Incident> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this line inflate our row
        View view = LayoutInflater.from(context).inflate(R.layout.row, null);

        return new MyHolder(view); // this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.mTitle.setText(models.get(position).getTitle());  //here is position
        holder.mDes.setText(models.get(position).getDescription());
        holder.mImageView.setImageResource(models.get(position).getImg());  //here i used image resource

    }

    @Override
    public int getItemCount() { return models.size();
    }
}
