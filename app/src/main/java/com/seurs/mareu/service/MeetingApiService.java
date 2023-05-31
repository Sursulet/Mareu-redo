package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    List<Meeting> getMeetings();
    void onAddMeeting(Meeting meeting);
    void onDeleteMeeting(Meeting meeting);

    List<Integer> getPlaces();
}
