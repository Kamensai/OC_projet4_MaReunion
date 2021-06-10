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

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MeetingFragment extends Fragment {

    private MeetingApiService mMeetingApiService;
    private RecyclerView mRecyclerView;
    private String mItemChosen;
    private Calendar mDateChosen;
    private final static String CHOSEN_DATE = "CHOSEN_DATE";
    private final static String MARIO = "Mario";
    private final static String LUIGI = "Luigi";
    private final static String PEACH = "Peach";
    private final static String ALL = "All";

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingApiService = DI.getMeetingApiService();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_reunion, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    // TODO : vérifier que la liste ne change pas après avoir supprimé une réunion dans les dates
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<ExampleMeeting> mMeetings = mMeetingApiService.getMeetings();
        List<ExampleMeeting> mListFiltered;
        DatePickerDialog mDatePicker;
        switch (item.getItemId()){
            case R.id.menu_Date:
                //Filtrer par Date
                mItemChosen = CHOSEN_DATE;
                Calendar mStartDate = Calendar.getInstance();
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                mDatePicker = new DatePickerDialog(this.getContext(),
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            mStartDate.set(year1,monthOfYear, dayOfMonth);
                            filterByDate(mStartDate);
                            mDateChosen = mStartDate;
                        }, year, month, day);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
                return true;
            case R.id.menu_Salle_Mario:
                mItemChosen = MARIO;
                mListFiltered = mMeetingApiService.getMeetingsByRooms(MARIO);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, this::clickOnDeleteListener));
                return true;
            case R.id.menu_Salle_Luigi:
                mItemChosen = LUIGI;
                mListFiltered = mMeetingApiService.getMeetingsByRooms(LUIGI);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, this::clickOnDeleteListener));
                return true;
            case R.id.menu_Salle_Peach:
                mItemChosen = PEACH;
                mListFiltered = mMeetingApiService.getMeetingsByRooms(PEACH);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, this::clickOnDeleteListener));
                return true;
            case R.id.menu_All:
                mItemChosen = ALL;
                Collections.sort(mMeetings, ExampleMeeting.MeetingDateComparator);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings, this::clickOnDeleteListener));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        List<ExampleMeeting> mMeetings = mMeetingApiService.getMeetings();
        final MyMeetingRecyclerViewAdapter adapter = (MyMeetingRecyclerViewAdapter) mRecyclerView.getAdapter();
        Collections.sort(mMeetings, ExampleMeeting.MeetingDateComparator);
        if (adapter != null && mItemChosen != null) {
            if (mItemChosen.equals(CHOSEN_DATE) && mDateChosen != null){
                List<ExampleMeeting> listFilteredByDate = mMeetingApiService.getMeetingsByDate(mDateChosen);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(listFilteredByDate, this::clickOnDeleteListener));
            }
            if (mItemChosen.equals(MARIO)){
                List<ExampleMeeting> listFilteredMario = mMeetingApiService.getMeetingsByRooms(MARIO);
                adapter.updateList(listFilteredMario);
            }
            if (mItemChosen.equals(LUIGI)){
                List<ExampleMeeting> listFilteredLuigi = mMeetingApiService.getMeetingsByRooms(LUIGI);
                adapter.updateList(listFilteredLuigi);
            }
            if (mItemChosen.equals(PEACH)){
                List<ExampleMeeting> listFilteredPeach = mMeetingApiService.getMeetingsByRooms(PEACH);
                adapter.updateList(listFilteredPeach);
            }
            else { adapter.updateList(mMeetings); }
        }
        else {
            mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings, this::clickOnDeleteListener));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    public void clickOnDeleteListener(ExampleMeeting meeting) {
        mMeetingApiService.deleteMeeting(meeting);
        initList();
    }

    private void filterByDate(Calendar dateChosen) {
        List<ExampleMeeting> mListFiltered;
        mListFiltered = mMeetingApiService.getMeetingsByDate(dateChosen);
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, this::clickOnDeleteListener));
    }
}
