package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    private static final List<String> DUMMY_MAILS = Arrays.asList("xyz@example", "abc@example", "uvw@example.com");
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("09:00", "Meeting A", "26/02/2020", DUMMY_MAILS),
            new Meeting("10:00", "Meeting B", "28/03/2020", DUMMY_MAILS),
            new Meeting("11:00", "Meeting C", "27/02/2020", DUMMY_MAILS),
            new Meeting("14:00", "Meeting D", "20/03/2020", DUMMY_MAILS),
            new Meeting("09:00", "Meeting E", "29/02/2020", DUMMY_MAILS),
            new Meeting("09:00", "Meeting F", "29/02/2020", DUMMY_MAILS)
    );
}
