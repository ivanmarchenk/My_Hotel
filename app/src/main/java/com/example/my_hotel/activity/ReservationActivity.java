package com.example.my_hotel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_hotel.R;
import com.example.my_hotel.adapter.classes.Guest;
import com.example.my_hotel.adapter.classes.User;
import com.example.my_hotel.main.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;

public class ReservationActivity extends AppCompatActivity {

    CalendarView calender;
    TextView date_view;

    Button btnSelectDate;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reservations;

    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        calender = (CalendarView) findViewById(R.id.calender);
        date_view = (TextView) findViewById(R.id.date_view);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date
                        = dayOfMonth + "-"
                        + (month + 1) + "-" + year;
                // установить эту дату в TextView для отображения
                date_view.setText(Date);
            }
        });

        /*Button galleryButton = (Button) findViewById(R.id.select);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ReservationActivity.this, GalleryActivity.class);
                startActivity(gallery);
            }
        });*/

        btnSelectDate = findViewById(R.id.select);
        root = findViewById(R.id.root_element_reservation);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://my-hotel-5c20f-default-rtdb.firebaseio.com");
        reservations = database.getReference("Reservations");
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationWindow();
            }
        });


    }

    private void showReservationWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Book a room");
        dialog.setMessage("Enter your details to book a room");
        LayoutInflater inflater = LayoutInflater.from(this);
        View reservationWindow = inflater.inflate(R.layout.reservation_window, null);
        dialog.setView(reservationWindow);
        final MaterialEditText email = reservationWindow.findViewById(R.id.emailField);
        final MaterialEditText checkInDate = reservationWindow.findViewById(R.id.CheckInDateField);
        final MaterialEditText checkOutDate = reservationWindow.findViewById(R.id.CheckOutDateField);
        //final MaterialEditText numberOfGuests = reservationWindow.findViewById(R.id.NumberGuestsField);
        final MaterialEditText phone = reservationWindow.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Book", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Input your email-address", Snackbar.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(checkInDate.getText().toString())){
                    Snackbar.make(root, "Input your check-in date", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(checkOutDate.getText().toString())){
                    Snackbar.make(root, "Input the check-out date", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                /*if(TextUtils.isEmpty(numberOfGuests.getText().toString())){
                    Snackbar.make(root, "Input the count of guests", Snackbar.LENGTH_SHORT).show();
                    return;
                }*/

                if(phone.getText().toString().length() < 5){
                    Snackbar.make(root, "Input your phone, that has more that 5 symbols", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Регистрация пользователя
                auth.createUserWithEmailAndPassword(email.getText().toString(), phone.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Guest guest = new Guest();
                                guest.setEmail(email.getText().toString());
                                guest.setCheckInDate(checkInDate.getText().toString());
                                guest.setCheckOutDate(checkOutDate.getText().toString());
                                guest.setPhone(phone.getText().toString());
                                //guest.setNumberOfGuests(numberOfGuests.getText().toString());


                                reservations.child(FirebaseAuth.getInstance().getCurrentUser()
                                        .getUid()).setValue(guest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Snackbar.make(root, "Booking request sent!", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(ReservationActivity.this, ReservationInfoActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Snackbar.make(root, "Reservation error. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
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

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_info_hotel:
                Intent hotel = new Intent(ReservationActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(ReservationActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(ReservationActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(ReservationActivity.this, GalleryActivity.class);
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