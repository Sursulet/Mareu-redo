package com.seurs.mareu.service;

import android.util.Log;

import com.seurs.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void onAddMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void onDeleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    private final List<Integer> mPlaces = DummyMeetingGenerator.generatePlaces();
    @Override
    public List<Integer> getPlaces() {
        return mPlaces;
    }

    @Override
    public List<Meeting> filterByDate() {
        Collections.sort(mMeetings, (m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        return mMeetings;
    }

    @Override
    public List<Meeting> filterByPlace() {
        Collections.sort(mMeetings, (m1, m2) -> m1.getPlace().compareTo(m2.getPlace()));
        return mMeetings;
    }
}
