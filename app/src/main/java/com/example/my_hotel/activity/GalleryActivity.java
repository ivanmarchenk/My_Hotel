package com.example.my_hotel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.my_hotel.adapter.classes.Photo;
import com.example.my_hotel.R;
import com.example.my_hotel.adapter.ImageGalleryAdapter;
import com.example.my_hotel.main.MainActivity;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.rv_images);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, Photo.getPhotos());
        recyclerView.setAdapter(adapter);
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
                Intent hotel = new Intent(GalleryActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(GalleryActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(GalleryActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_settings:
                //infoTextView.setText("action_settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}