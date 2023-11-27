package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateData extends AppCompatActivity {

    // creating variables for our edit text
    private EditText nameEdt, addressEdt, phoneEdt;

    // creating a strings for storing our values from Edittext fields.
    private String name, address, phone;

    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        User users = (User) getIntent().getSerializableExtra("user");

        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        nameEdt = findViewById(R.id.idEdtName);
        addressEdt = findViewById(R.id.idEdtAddress);
        phoneEdt = findViewById(R.id.idEdtPhone);

        // creating variable for button
        Button updateDataBtn = findViewById(R.id.idBtnUpdateData);
        Button dltDataBtn=findViewById(R.id.idBtnDeleteData);


        nameEdt.setText(users.getName());
        addressEdt.setText(users.getAddress());
        phoneEdt.setText(users.getPhone());

        dltDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to delete the course.
                deleteUser(users);
            }
        });

        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdt.getText().toString();
                address = addressEdt.getText().toString();
                phone = phoneEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(name)) {
                    nameEdt.setError("Please enter Name");
                } else if (TextUtils.isEmpty(address)) {
                   addressEdt.setError("Please enter Address");
                } else if (TextUtils.isEmpty(phone)) {
                    phoneEdt.setError("Please enter Phone");
                } else {
                    // calling a method to update our course.
                    // we are passing our object class, course name,
                    // course description and course duration from our edittext field.
                    updateData(users, name, address, phone);
                }
            }
        });
    }


//    dltDataBtn.set

    private void deleteUser(User users) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Users").
                // after that we are getting the document
                // which we have to delete.
                        document(users.getId()).

                // after passing the document id we are calling
                // delete method to delete this document.
                        delete().
                // after deleting call on complete listener
                // method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // inside on complete method we are checking
                        // if the task is success or not.
                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(UpdateData.this, "Data has been deleted from Database.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateData.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(UpdateData.this, "Fail to delete the data. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateData(User users, String name, String address, String phone) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
         User updatedData = new User(name, address, phone);

        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Users").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(users.getId()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(updatedData).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        Toast.makeText(UpdateData.this, "User Data has been updated..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateData.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
