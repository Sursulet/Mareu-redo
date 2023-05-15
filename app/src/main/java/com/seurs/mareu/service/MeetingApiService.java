package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    List<Meeting> getMeetings();
    void onDeleteMeeting(Meeting meeting);
}
