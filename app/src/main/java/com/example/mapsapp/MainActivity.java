package com.example.mapsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {

    GoogleMap gMap;
    SeekBar seekWidth,seekRed,seekGreen,seekBlue;
    Button btDraw,btClear;

    Polyline polyline = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    int red =0, green =0,blue= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        seekWidth = findViewById(R.id.seek_width);
        seekRed = findViewById(R.id.seek_red);
        seekGreen = findViewById(R.id.seek_green);
        seekBlue = findViewById(R.id.seek_blue);
        btDraw=findViewById(R.id.bt_draw);
        btClear = findViewById(R.id.bt_clear);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        btDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Draw Polyline on Map
                if (polyline !=null ) polyline.remove();
                //Create PolylineOptions
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(latLngList).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);
                polyline.setColor(Color.rgb(red,green,blue));
                setWidth();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polyline!=null) polyline.remove();
                for(Marker marker: markerList) marker.remove();
                latLngList.clear();
                seekWidth.setProgress(3);
                seekRed.setProgress(0);
                seekGreen.setProgress(0);
                seekBlue.setProgress(0);
            }
        });
        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);
    }

    private void setWidth() {
        seekWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int width = seekWidth.getProgress();
                if (polyline!=null)
                    polyline.setWidth(width);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap=googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions().position(latLng);

                Marker marker = gMap.addMarker(markerOptions);

                latLngList.add(latLng);
                markerList.add(marker);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seek_red:
                red = i;
                break;
            case R.id.seek_green:
                green = i;
                break;
            case R.id.seek_blue:
                blue=i;
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}