package com.khamvongsa.victor.mareunion.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.khamvongsa.victor.mareunion.R;
import com.khamvongsa.victor.mareunion.service.DeleteListener;
import com.khamvongsa.victor.mareunion.service.FakeReunionApiService;
import com.khamvongsa.victor.mareunion.service.ReunionApiService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    ListReunionPagerAdapter mPagerAdapter;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListReunionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Date:
                //sort by Date
                Toast.makeText(MainActivity.this,"sort by Date", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_Salle:
                Toast.makeText(MainActivity.this,"sort by Salle", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_Reunion)
    void addReunion() {
        AddReunionActivity.navigate(this);
    }

}