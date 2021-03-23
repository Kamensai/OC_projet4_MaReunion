package com.khamvongsa.victor.mareunion.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.service.FakeReunionApiService;
import com.khamvongsa.victor.mareunion.service.ReunionApiService;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ReunionFragment extends Fragment {

    private ReunionApiService mReunionApiService;
    private RecyclerView mRecyclerView;

    public static ReunionFragment newInstance() {
        return new ReunionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReunionApiService = DI.getReunionApiService();
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
    private void initList() {
        List<ExempleReunion> mReunions = mReunionApiService.getReunions();
        final MyReunionRecyclerViewAdapter adapter = (MyReunionRecyclerViewAdapter) mRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateList(mReunions);
        }
        else {
            mRecyclerView.setAdapter(new MyReunionRecyclerViewAdapter(mReunions, reunion -> clickOnDeleteListener(reunion)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    // TODO : Ajouter une méthode de suppression de réunion
    public void clickOnDeleteListener(ExempleReunion reunion) {
        mReunionApiService.clickOnDeleteListener(reunion);
    }
}
