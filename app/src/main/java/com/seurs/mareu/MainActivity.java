package com.seurs.mareu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.seurs.mareu.di.DI;
import com.seurs.mareu.model.Meeting;
import com.seurs.mareu.service.MeetingApiService;
import com.seurs.mareu.ui.MeetingAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MeetingApiService mService;

    MaterialToolbar mToolbar;
    FloatingActionButton mFab;
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = DI.getMeetingApiService();

        mToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);
        //mToolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.baseline_filter_list_24));

        mFab = findViewById(R.id.on_add_meeting);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a new meeting", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        mRecyclerview = findViewById(R.id.meeting_list);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<Meeting> mMeetings = mService.getMeetings();
        MeetingAdapter mAdapter = new MeetingAdapter(mMeetings);
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.filter_by_date) {
            return true;
        } else if (itemId == R.id.filter_by_place) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}