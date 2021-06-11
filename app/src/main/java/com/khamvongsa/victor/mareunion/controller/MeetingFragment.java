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
    private final static String CHOSEN_ITEM = "CHOSEN_ITEM";
    private final static String CHOSEN_DATE = "CHOSEN_DATE";
    private final static String CHOSEN_DATE_YEAR = "CHOSEN_DATE_YEAR";
    private final static String CHOSEN_DATE_MONTH = "CHOSEN_DATE_MONTH";
    private final static String CHOSEN_DATE_DAY = "CHOSEN_DATE_DAY";
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
        if (savedInstanceState != null) {
            mItemChosen = savedInstanceState.getString(CHOSEN_ITEM);
            if (mItemChosen != null && mItemChosen.equalsIgnoreCase(CHOSEN_DATE) ){
                mDateChosen = Calendar.getInstance();
                mDateChosen.set(savedInstanceState.getInt(CHOSEN_DATE_YEAR), savedInstanceState.getInt(CHOSEN_DATE_MONTH), savedInstanceState.getInt(CHOSEN_DATE_DAY));
            }
        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<ExampleMeeting> mMeetings = mMeetingApiService.getMeetings();
        switch (item.getItemId()){
            case R.id.menu_Date:
                //Filtrer par Date
                mItemChosen = CHOSEN_DATE;
                showDatePickerDialog();
                return true;
            case R.id.menu_Salle_Mario:
                mItemChosen = MARIO;
                updateListFiltered();
                return true;
            case R.id.menu_Salle_Luigi:
                mItemChosen = LUIGI;
                updateListFiltered();
                return true;
            case R.id.menu_Salle_Peach:
                mItemChosen = PEACH;
                updateListFiltered();
                return true;
            case R.id.menu_All:
                mItemChosen = ALL;
                Collections.sort(mMeetings, ExampleMeeting.MeetingDateComparator);
                mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings, this::clickOnDeleteListener));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateListFiltered() {
        List<ExampleMeeting> mListFiltered;
        mListFiltered = mMeetingApiService.getMeetingsByRooms(mItemChosen);
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mListFiltered, this::clickOnDeleteListener));
    }

    private void showDatePickerDialog() {
        DatePickerDialog mDatePicker;
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
            else if (mItemChosen.equals(MARIO) || mItemChosen.equals(LUIGI) || mItemChosen.equals(PEACH)){
                adapter.updateList(mMeetingApiService.getMeetingsByRooms(mItemChosen));
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle out) {
        super.onSaveInstanceState(out);
        if (mItemChosen != null) {
            out.putString(CHOSEN_ITEM, mItemChosen);
        }
        if (mDateChosen != null) {
            out.putInt(CHOSEN_DATE_YEAR, mDateChosen.get(Calendar.YEAR));
            out.putInt(CHOSEN_DATE_MONTH, mDateChosen.get(Calendar.MONTH));
            out.putInt(CHOSEN_DATE_DAY, mDateChosen.get(Calendar.DAY_OF_MONTH));
        }
    }
}
