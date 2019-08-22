package com.example.salondeniz.Model;

import com.google.firebase.database.DatabaseReference;

public class Clock {


    private String date;
    private String clockID;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getclockID() {
        return clockID;
    }

    public void setClockID(String  clockID) {
        this.clockID = clockID;
    }
}
