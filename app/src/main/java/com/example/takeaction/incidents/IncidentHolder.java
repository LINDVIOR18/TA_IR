package com.example.takeaction.incidents;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takeaction.R;

class IncidentHolder extends RecyclerView.ViewHolder {

    ImageView mImaeView;
    TextView mTitle, mDes;

    IncidentHolder(@NonNull View itemView) {
        super(itemView);

        this.mImaeView = itemView.findViewById(R.id.imageIv);
        this.mTitle = itemView.findViewById(R.id.titleTv);
        this.mDes = itemView.findViewById(R.id.descriptionTv);
    }

    void setClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}