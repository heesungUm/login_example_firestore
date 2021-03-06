package com.appplepie.login_example_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button registerBtn, loginBtn;
    EditText idEt, pwEt;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        loginBtn = findViewById(R.id.btn_login);
        registerBtn = findViewById(R.id.btn_to_register);
        idEt = findViewById(R.id.et_login_id);
        pwEt = findViewById(R.id.et_login_password);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id, password;

                id = idEt.getText().toString();
                password = pwEt.getText().toString();

                db.collection("users").document(id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){

                                    String data = task.getResult().getData()+""; //?????? ???????????? String ????????? ??????

                                    try {

                                        JSONObject jsonObject = new JSONObject(data); // ????????????????????? ?????? json?????? ??????????????? ?????? ???????????? jsonObject??? ???????????? ??????

                                        if (jsonObject.getString("pwd").equals(password)){
                                            Toast.makeText(MainActivity.this, "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "??????????????? ????????????????????????.", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
            }
        });


    }
}