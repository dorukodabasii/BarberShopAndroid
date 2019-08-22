package com.example.salondeniz;

public class AppointmentsDetail {
    private String appointmentID;
    private String appointmentDetailID;
    private String operation;


    public String getAppointmentDetailID() {
        return appointmentDetailID;
    }

    public void setAppointmentDetailID(String appointmentDetailID) {
        this.appointmentDetailID = appointmentDetailID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
