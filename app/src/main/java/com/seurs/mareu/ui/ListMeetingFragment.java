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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMeetingFragment extends Fragment {

    private MeetingApiService mService;
    private List<Meeting> mMeetings;
    private FragmentListMeetingBinding binding;
    MeetingAdapter mAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMeetingFragment newInstance(String param1, String param2) {
        ListMeetingFragment fragment = new ListMeetingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        binding.onAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListMeetingFragment.this).navigate(R.id.action_ListMeetingFragment_to_AddMeetingFragment);
                Snackbar.make(view, "Add a new meeting", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        binding.meetingList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.meetingList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new MeetingAdapter.OnClickListener() {
            @Override
            public void onDeleteClick(int position) {
                mService.onDeleteMeeting(mMeetings.get(position));
                mAdapter.notifyItemRemoved(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}