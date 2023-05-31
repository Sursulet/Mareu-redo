package com.seurs.mareu.service;

import com.seurs.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    private static final List<String> DUMMY_MAILS = Arrays.asList("xyz@example.com", "abc@example.com", "uvw@example.com");
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting( "Meeting A", "PEACH", 0xffef9a9a, "26/02/2020","09:00", DUMMY_MAILS),
            new Meeting( "Meeting B", "PEACH", 0xffa5d6a7, "28/03/2020","10:00", DUMMY_MAILS),
            new Meeting( "Meeting C", "PEACH", 0xffa5d6a7, "27/02/2020","11:00", DUMMY_MAILS),
            new Meeting( "Meeting D", "PEACH", 0xffef9a9a, "20/03/2020","14:00", DUMMY_MAILS),
            new Meeting( "Meeting E", "PEACH", 0Xff90caf9, "29/02/2020","09:00", DUMMY_MAILS),
            new Meeting( "Meeting F", "PEACH", 0xfffff59d, "29/02/2020","09:00", DUMMY_MAILS)
    );

    static List<Integer> generatePlaces() {
        return new ArrayList<>(DUMMY_PLACES);
    }

    public static List<Integer> DUMMY_PLACES = Arrays.asList(
            0xffef9a9a,
            0xffa5d6a7,
            0Xff90caf9,
            0xfffff59d/*,
            0xffffccbc,
            Color.RED,
            Color.GREEN,
            Color.YELLOW*/
    );
}
