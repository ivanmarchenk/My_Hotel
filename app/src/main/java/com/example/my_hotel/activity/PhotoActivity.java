package com.example.my_hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.my_hotel.adapter.classes.Photo;
import com.example.my_hotel.R;
import com.example.my_hotel.main.MainActivity;

public class PhotoActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTO = "PhotoActivity.PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mImageView = (ImageView) findViewById(R.id.image);
        Photo Photo = getIntent().getParcelableExtra(EXTRA_PHOTO);

        Glide.with(this)
                .asBitmap()
                .load(Photo.getUrl())
                .error(R.drawable.ic_cloud_off_red)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mImageView);
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
                Intent hotel = new Intent(PhotoActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(PhotoActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(PhotoActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(PhotoActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(PhotoActivity.this, GalleryActivity.class);
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
