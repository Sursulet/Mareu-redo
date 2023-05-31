package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

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
}
