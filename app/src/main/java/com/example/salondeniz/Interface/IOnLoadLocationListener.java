package com.example.salondeniz.Interface;
import com.example.salondeniz.MyLatLng;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface IOnLoadLocationListener {

    void onLoadLocationSuccess(List<MyLatLng> latLngs);
    void onLoadLocationFailed(String message);


}

