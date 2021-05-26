package com.khamvongsa.victor.mareunion.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MeetingFragment extends Fragment {

    private MeetingApiService mMeetingApiService;
    private RecyclerView mRecyclerView;

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingApiService = DI.getReunionApiService();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_reunion, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<ExampleMeeting> mReunions = mMeetingApiService.getMeetings();
        List<ExampleMeeting> mListFiltered;
        DatePickerDialog mDatePicker;
        Calendar mStartDate = Calendar.getInstance();
        switch (item.getItemId()){
            case R.id.menu_Date:
                //sort by Date
                Toast.makeText(this.getContext(),"sort by Date", Toast.LENGTH_SHORT).show();

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mDatePicker = new DatePickerDialog(this.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mStartDate.set(year,monthOfYear, dayOfMonth);
                                filterByDate(mStartDate);
                            }
                        }, year, month, day);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
                return true;
            case R.id.menu_Salle_Mario:
                Toast.makeText(this.getContext(),"Filter only Mario", Toast.LENGTH_SHORT).show();
                mListFiltered = mMeetingApiService.getMeetingsByRooms("Mario");
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, reunion -> clickOnDeleteListener(reunion)));
                return true;
            case R.id.menu_Salle_Luigi:
                Toast.makeText(this.getContext(),"Filter only Luigi", Toast.LENGTH_SHORT).show();
                mListFiltered = mMeetingApiService.getMeetingsByRooms("Luigi");
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, reunion -> clickOnDeleteListener(reunion)));
                return true;
            case R.id.menu_Salle_Peach:
                Toast.makeText(this.getContext(),"Filter only Peach", Toast.LENGTH_SHORT).show();
                mListFiltered = mMeetingApiService.getMeetingsByRooms("Peach");
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, reunion -> clickOnDeleteListener(reunion)));
                return true;
            case R.id.menu_All:
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mReunions, reunion -> clickOnDeleteListener(reunion)));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        List<ExampleMeeting> mReunions = mMeetingApiService.getMeetings();
        final MyMeetingRecyclerViewAdapter adapter = (MyMeetingRecyclerViewAdapter) mRecyclerView.getAdapter();
        Collections.sort(mReunions, ExampleMeeting.MeetingDateComparator);
        if (adapter != null) {
            adapter.updateList(mReunions);
        }
        else {
            mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mReunions, reunion -> clickOnDeleteListener(reunion)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    public void clickOnDeleteListener(ExampleMeeting reunion) {
        mMeetingApiService.deleteMeeting(reunion);
        initList();
    }

    private void filterByDate(Calendar dateChoisie) {
        List<ExampleMeeting> mListFiltered;
        mListFiltered = mMeetingApiService.getMeetingsByDate(dateChoisie);
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, reunion -> clickOnDeleteListener(reunion)));
    }
}
