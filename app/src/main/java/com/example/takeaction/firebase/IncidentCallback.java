package com.example.takeaction.firebase;

import com.example.takeaction.model.IncidentModel;

import java.util.List;

public interface IncidentCallback {
    void onDataSuccess(List<IncidentModel> incidentModels);
}
