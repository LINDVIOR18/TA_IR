package com.example.takeaction.incidents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takeaction.R;

import java.util.ArrayList;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentHolder> {

    private Context context;
    private ArrayList<IncidentList> models;
    private Callback callback;

    public IncidentAdapter(Context c, ArrayList<IncidentList> models, Callback callback) {
        this.context = c;
        this.models = models;
        this.callback = callback;
    }

    @NonNull
    @Override
    public IncidentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incident_row, null);

        return new IncidentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentHolder myHolder, final int position) {
        myHolder.mTitle.setText(models.get(position).getTitle());
        myHolder.mDes.setText(models.get(position).getDescription());
        myHolder.mImaeView.setImageResource(models.get(position).getImg());
        myHolder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface Callback {
        void onItemClick(int position);
    }
}