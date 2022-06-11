package com.example.my_hotel.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.my_hotel.R;
import com.example.my_hotel.activity.GalleryActivity;
import com.example.my_hotel.activity.HotelActivity;
import com.example.my_hotel.activity.LocationActivity;
import com.example.my_hotel.activity.ReservationActivity;
import com.example.my_hotel.database.DatabaseHelper;
import com.example.my_hotel.main.MainActivity;

public class RestaurantMenuActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView userList;
    EditText userFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        userList = (ListView)findViewById(R.id.menuList);
        userFilter = (EditText)findViewById(R.id.menuFilter);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COAST};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            // если в текстовом поле есть текст, выполняем фильтрацию
            // данная проверка нужна при переходе от одной ориентации экрана к другой
            if(!userFilter.getText().toString().isEmpty())
                userAdapter.getFilter().filter(userFilter.getText().toString());
            // установка слушателя изменения текста
            // при изменении текста выполняем фильтрацию

            userFilter.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) { }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    userAdapter.getFilter().filter(s.toString());
                }
            });
            // устанавливаем провайдер фильтрации
            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    if (constraint == null || constraint.length() == 0) {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
                    }
                    else {
                        return db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                                DatabaseHelper.COLUMN_NAME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });
            userList.setAdapter(userAdapter);
        }
        catch (SQLException ex){}
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
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
                Intent hotel = new Intent(RestaurantMenuActivity.this, HotelActivity.class);
                startActivity(hotel);
                return true;
            case R.id.action_info_program:
                Intent main = new Intent(RestaurantMenuActivity.this, MainActivity.class);
                startActivity(main);
                return true;
            case R.id.action_location:
                Intent location = new Intent(RestaurantMenuActivity.this, LocationActivity.class);
                startActivity(location);
                return true;
            case R.id.action_reservation:
                Intent reservation = new Intent(RestaurantMenuActivity.this, ReservationActivity.class);
                startActivity(reservation);
                return true;
            case R.id.action_room:
                Intent gallery = new Intent(RestaurantMenuActivity.this, GalleryActivity.class);
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