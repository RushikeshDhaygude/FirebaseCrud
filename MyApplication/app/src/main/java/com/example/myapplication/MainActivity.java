package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    private EditText nameEdt, addressEdt, phoneEdt;
    private Button submitBtn,viewBtn;
    private String name, address, phone;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();

        nameEdt = findViewById(R.id.idEdtName);
        addressEdt = findViewById(R.id.idEdtAddress);
        phoneEdt = findViewById(R.id.idEdtPhone);
        submitBtn = findViewById(R.id.idBtnSubmitData);
        viewBtn=findViewById(R.id.idBtnViewhData);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewData.class);
                startActivity(intent);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = nameEdt.getText().toString();
                address = addressEdt.getText().toString();
                phone = phoneEdt.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    nameEdt.setError("Please enter Name");
                } else if (TextUtils.isEmpty(address)) {
                    addressEdt.setError("Please enter Address");
                } else if (TextUtils.isEmpty(phone)) {
                    phoneEdt.setError("Please enter Phone Number");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(name, address, phone);
                }
            }
        });
    }

    private void addDataToFirestore(String name, String address, String phone) {


        CollectionReference dbCourses = db.collection("Users");


        User users = new User(name, address, phone);


        dbCourses.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(MainActivity.this, "User Data has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to add Data \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
