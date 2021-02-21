package com.khamvongsa.victor.mareunion.controller;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khamvongsa.victor.mareunion.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private final ExempleReunion mReunions;

    public MyReunionRecyclerViewAdapter(ExempleReunion item) { mReunions = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reunion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mSalleName.setText(mReunions.getSalle().getNom());

        holder.mSujet.setText(mReunions.getSujet());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_color)
        public ImageView mSalleColor;
        @BindView(R.id.item_list_salle)
        public TextView mSalleName;
        @BindView(R.id.item_list_sujet)
        public TextView mSujet;
        @BindView(R.id.item_list_dateDebut)
        public TextView mDateDebut;
        @BindView(R.id.item_list_participants)
        public TextView mParticipants;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
