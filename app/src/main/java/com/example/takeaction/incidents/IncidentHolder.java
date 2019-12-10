package com.example.takeaction.incidents;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takeaction.R;

class IncidentHolder extends RecyclerView.ViewHolder {

    ImageView ivCategory;
    TextView tvTitle;
    TextView tvDescription;
    TextView tvDate;

    IncidentHolder(@NonNull View itemView) {
        super(itemView);

        this.ivCategory = itemView.findViewById(R.id.imageIv);
        this.tvTitle = itemView.findViewById(R.id.titleTv);
        this.tvDescription = itemView.findViewById(R.id.descriptionTv);
        this.tvDate = itemView.findViewById(R.id.tv_date);
    }

    void setClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}