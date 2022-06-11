package com.example.my_hotel.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.my_hotel.R;
import com.example.my_hotel.database.RestaurantMenuActivity;
import com.example.my_hotel.activity.FeedbackActivity;
import com.example.my_hotel.activity.GalleryActivity;
import com.example.my_hotel.activity.HotelActivity;
import com.example.my_hotel.activity.LocationActivity;
import com.example.my_hotel.activity.ReservationActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        ImageView imageView = new ImageView(this);
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.hotel, null);
        // применяем ресурс
        imageView.setImageDrawable(drawable);
        Button hotelButton = (Button) findViewById(R.id.hotel);
        hotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotel = new Intent(MainActivity.this, HotelActivity.class);
                startActivity(hotel);
            }
        });
        Button galleryButton = (Button) findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(gallery);
            }
        });
        Button feedbackButton = (Button) findViewById(R.id.feedback);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(feedback);
                //FeedbackActivity.showRatingDialog();

            }
        });
        Button locationButton = (Button) findViewById(R.id.location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent location = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(location);
            }
        });
        Button reservationButton = (Button) findViewById(R.id.reservation);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reservation = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(reservation);
            }
        });
        Button restaurantButton = (Button) findViewById(R.id.restaurant);
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurant = new Intent(MainActivity.this, RestaurantMenuActivity.class);
                startActivity(restaurant);
            }
        });


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
                Intent hotel = new Intent(MainActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(MainActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(gallery);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}