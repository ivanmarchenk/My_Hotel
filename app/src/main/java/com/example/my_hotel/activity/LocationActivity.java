package com.example.my_hotel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.my_hotel.R;
import com.example.my_hotel.main.MainActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class LocationActivity extends AppCompatActivity {

    private MapView mapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("79d9b22c-0a38-4574-b365-350915136fe0");
        MapKitFactory.initialize(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        setContentView(R.layout.activity_location);
        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        //TextView infoTextView = findViewById(R.id.textView);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_info_hotel:
                Intent hotel = new Intent(LocationActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(LocationActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(LocationActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(LocationActivity.this, GalleryActivity.class);
                startActivity(gallery);
                return true;
            case R.id.action_settings:
                //infoTextView.setText("action_settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}