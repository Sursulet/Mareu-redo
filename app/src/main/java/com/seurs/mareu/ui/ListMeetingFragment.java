package com.seurs.mareu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.seurs.mareu.R;
import com.seurs.mareu.databinding.FragmentListMeetingBinding;
import com.seurs.mareu.di.DI;
import com.seurs.mareu.model.Meeting;
import com.seurs.mareu.service.MeetingApiService;

import java.util.List;

public class ListMeetingFragment extends Fragment {

    private MeetingApiService mService;
    private List<Meeting> mMeetings;
    private FragmentListMeetingBinding binding;
    MeetingAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = DI.getMeetingApiService();
        mMeetings = mService.getMeetings();
        mAdapter = new MeetingAdapter(mMeetings);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListMeetingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.topAppBar.inflateMenu(R.menu.menu);
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.filter_by_date) {
                return true;
            } else if (item.getItemId() == R.id.filter_by_place) {
                return true;
            }
            return false;
        });

        binding.onAddMeeting.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(ListMeetingFragment.this).navigate(R.id.action_ListMeetingFragment_to_AddMeetingFragment);
            Snackbar.make(view1, "Add a new meeting", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        });

        binding.meetingList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.meetingList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(position -> {
            mService.onDeleteMeeting(mMeetings.get(position));
            mAdapter.notifyItemRemoved(position);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}