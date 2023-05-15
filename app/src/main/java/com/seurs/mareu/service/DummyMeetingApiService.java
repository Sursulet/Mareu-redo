package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void onDeleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }
}
