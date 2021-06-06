package com.khamvongsa.victor.mareunion.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.service.DeleteListener;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {


    private final List<ExampleMeeting> mMeetings;
    private final DeleteListener mDeleteListener;
    final SimpleDateFormat mFormat = new SimpleDateFormat("dd-MM-yy");
    final SimpleDateFormat mFormatHour = new SimpleDateFormat("HH'h ' mm'm'");

    public MyMeetingRecyclerViewAdapter(List<ExampleMeeting> item, DeleteListener mDeleteListener) { mMeetings = item;
    this.mDeleteListener = mDeleteListener;
    }

    public void updateList(List<ExampleMeeting> item) {
        mMeetings.clear();
        mMeetings.addAll(item);
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
        ExampleMeeting meeting = mMeetings.get(position);
        holder.mRoomName.setText(meeting.getRoom().getName());
        holder.mRoomColor.setBackgroundColor(meeting.getRoom().getColor());
        holder.mSubject.setText(meeting.getSubject());
        holder.mDateStart.setText(mFormat.format(meeting.getStartDate()));
        holder.mStartHour.setText(mFormatHour.format(meeting.getStartHour()));
        holder.mEndHour.setText(mFormatHour.format(meeting.getEndHour()));
        holder.mParticipants.setText(meeting.getParticipant().toString());
        holder.mParticipants.setText(meeting.getParticipant().toString());

        holder.mDeleteButton.setOnClickListener(v -> mDeleteListener.clickOnDeleteListener(meeting));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_color)
        public ImageView mRoomColor;
        @BindView(R.id.item_list_room)
        public TextView mRoomName;
        @BindView(R.id.item_list_subject)
        public TextView mSubject;
        @BindView(R.id.item_list_dateStart)
        public TextView mDateStart;
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
