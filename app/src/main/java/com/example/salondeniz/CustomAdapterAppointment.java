package com.example.salondeniz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.salondeniz.Model.Appointment;

import java.util.ArrayList;

public class CustomAdapterAppointment extends ArrayAdapter<String> {
    ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
    ArrayList<String> appointments = new ArrayList<String>();
    Context context;
    CustomAdapterAppointment(Context c, ArrayList<String> a, ArrayList<Appointment> k){
        super(c,R.layout.appointment_tek_satir,R.id.txtRandevuBilgi,a);
        context=c;
        appointments=a;
        appointmentArrayList=k;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ınflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tek_satir = ınflater.inflate(R.layout.appointment_tek_satir,parent,false);

        TextView randevuBilgi = tek_satir.findViewById(R.id.txtRandevuBilgi);

        randevuBilgi.setText(appointments.get(position));

        return super.getView(position, convertView, parent);
    }

}
