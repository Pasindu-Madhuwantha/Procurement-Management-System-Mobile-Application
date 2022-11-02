package com.example.product_management;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {

    EditText userName, userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userName = findViewById(R.id.uname);
        userpassword = findViewById(R.id.password);
    }

    public void login(View v) {
        String uName = userName.getText().toString();
        String uPassword = userpassword.getText().toString();
        if (uName.equals("Admin") && uPassword.equals("product")) {
            Intent i = new Intent(this, MainActivity6.class);
            startActivity(i);
        } else if (uName.equals("") && uPassword.equals("")) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(LoginActivity.this, "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }

    }
}


