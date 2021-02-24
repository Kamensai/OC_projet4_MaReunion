package com.khamvongsa.victor.mareunion.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khamvongsa.victor.mareunion.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private final ExempleReunion mReunions;
    SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");

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
        holder.mSalleColor.setBackgroundColor(mReunions.getSalle().getCouleur());
        holder.mSujet.setText(mReunions.getSujet());
        holder.mDateDebut.setText(formater.format(mReunions.getDebut()));
        holder.mParticipants.setText(mReunions.getParticipant().toString());
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
