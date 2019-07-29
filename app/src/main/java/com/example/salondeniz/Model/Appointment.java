package com.example.salondeniz.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Appointment {
    private String barberID;
    private String userID;
    private String clockID;
    private String operations;
    private String favorite;
}
