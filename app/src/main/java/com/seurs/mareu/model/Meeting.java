package com.seurs.mareu.model;

import java.util.List;

public class Meeting {
    String hour;
    String place;
    String topic;
    List<String> participants;

    public Meeting(String hour, String place, String topic, List<String> participants) {
        this.hour = hour;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
    }

    // Getter
    public String getHour() {
        return hour;
    }

    public String getPlace() {
        return place;
    }

    public String getTopic() {
        return topic;
    }

    public List<String> getParticipants() {
        return participants;
    }

    // Setter
    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
