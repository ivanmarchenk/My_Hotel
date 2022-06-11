package com.example.my_hotel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.my_hotel.R;
import com.example.my_hotel.main.MainActivity;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
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
            case R.id.action_info_program:
                Intent main = new Intent(HotelActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(HotelActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(HotelActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(HotelActivity.this, GalleryActivity.class);
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