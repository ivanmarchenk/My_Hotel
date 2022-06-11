package com.example.my_hotel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.my_hotel.R;
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

public class LoginActivity extends AppCompatActivity {

    Button btnSignIN, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignIN = findViewById(R.id.buttonSignIn);
        btnRegister = findViewById(R.id.buttonRegister);
        root = findViewById(R.id.root_element);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://my-hotel-5c20f-default-rtdb.firebaseio.com");
        users = database.getReference("Users");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        btnSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sign in");
        dialog.setMessage("Input your data for sign in account");
        LayoutInflater inflater = LayoutInflater.from(this);
        View signInWindow = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signInWindow);
        final MaterialEditText email = signInWindow.findViewById(R.id.emailField);
        final MaterialEditText pass = signInWindow.findViewById(R.id.passField);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Input your email-address", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(pass.getText().toString().length() < 5){
                    Snackbar.make(root, "Input your pass, that has more that 5 symbols", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //Авторизация пользователя
                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                                Snackbar.make(root, "Authorisation Error. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                });
            }
        });


        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Input your data for register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWindow = inflater.inflate(R.layout.register_window, null);
        dialog.setView(registerWindow);

        final MaterialEditText email = registerWindow.findViewById(R.id.emailField);
        final MaterialEditText pass = registerWindow.findViewById(R.id.passField);
        final MaterialEditText name = registerWindow.findViewById(R.id.nameField);
        final   MaterialEditText phone = registerWindow.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Input your email-address", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Input your name", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(root, "Input your phone", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(pass.getText().toString().length() < 5){
                    Snackbar.make(root, "Input your pass, that has more that 5 symbols", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Регистрация пользователя
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setName(name.getText().toString());
                                user.setPhone(phone.getText().toString());
                                user.setPass(pass.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser()
                                        .getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Snackbar.make(root, "User added!", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Snackbar.make(root, "Registration error. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });


        dialog.show();
    }
}