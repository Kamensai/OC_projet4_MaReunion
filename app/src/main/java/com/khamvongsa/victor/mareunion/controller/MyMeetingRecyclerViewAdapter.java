package com.khamvongsa.victor.mareunion.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.service.DeleteListener;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {


    private List<ExampleMeeting> mReunions;
    private final DeleteListener mDeleteListener;
    SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");
    SimpleDateFormat formaterHour = new SimpleDateFormat("HH'h ' mm'm'");

    public MyMeetingRecyclerViewAdapter(List<ExampleMeeting> item, DeleteListener mDeleteListener) { mReunions = item;
    this.mDeleteListener = mDeleteListener;
    }

    public void updateList(List<ExampleMeeting> item) {
        mReunions.clear();
        mReunions.addAll(item);
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ExampleMeeting reunion = mReunions.get(position);
        holder.mSalleName.setText(reunion.getRoom().getNom());
        holder.mSalleColor.setBackgroundColor(reunion.getRoom().getCouleur());
        holder.mSujet.setText(reunion.getSubject());
        holder.mDateDebut.setText(formater.format(reunion.getDebut()));
        holder.mStartHour.setText(formaterHour.format(reunion.getStartHour()));
        holder.mEndHour.setText(formaterHour.format(reunion.getEndHour()));
        holder.mParticipants.setText(reunion.getParticipant().toString());
        holder.mParticipants.setText(reunion.getParticipant().toString());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mDeleteListener.clickOnDeleteListener(reunion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReunions.size();
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
        @BindView(R.id.item_list_startHour)
        public TextView mStartHour;
        @BindView(R.id.item_list_EndHour)
        public TextView mEndHour;
        @BindView(R.id.item_list_participants)
        public TextView mParticipants;

        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;


        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
