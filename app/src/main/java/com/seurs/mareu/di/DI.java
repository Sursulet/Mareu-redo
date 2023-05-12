package com.seurs.mareu.di;

import com.seurs.mareu.service.DummyMeetingApiService;
import com.seurs.mareu.service.MeetingApiService;

public class DI {
    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
