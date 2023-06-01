package com.seurs.mareu.model;

import java.util.List;

public class Meeting {
    String topic;
    String manager;
    Integer place;
    String date;
    String hour;
    List<String> participants;

    public Meeting(String topic, String manager, Integer place, String date, String hour, List<String> participants) {
        this.topic = topic;
        this.manager = manager;
        this.place = place;
        this.date = date;
        this.hour = hour;
        this.participants = participants;
    }

    // Getter
    public String getHour() {
        return hour;
    }

    public String getManager() {
        return manager;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    // Setter
    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setManager(String place) {
        this.manager = place;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
