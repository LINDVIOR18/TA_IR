package com.example.takeaction.incidents;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takeaction.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentHolder> {

    private ArrayList<IncidentList> models;
    private Callback callback;

    IncidentAdapter(ArrayList<IncidentList> models, Callback callback) {
        this.models = models;
        this.callback = callback;
    }

    @NonNull
    @Override
    public IncidentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incident_row, null);

        return new IncidentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentHolder myHolder, final int position) {

        myHolder.tvTitle.setText(models.get(position).getTitle());
        myHolder.tvDescription.setText(models.get(position).getDescription());
        myHolder.ivCategory.setImageResource(models.get(position).getImg());
        myHolder.tvDate.setText(getFormatedDate(Calendar.getInstance().getTimeInMillis()));
        myHolder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(position);
                }
            }
        });
    }

    private String getFormatedDate(long date) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
        return df.format(new Date(date));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface Callback {
        void onItemClick(int position);
    }
}