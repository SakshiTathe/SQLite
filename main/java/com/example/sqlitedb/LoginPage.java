package com.example.sqlitedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginPage extends AppCompatActivity {
    EditText logname,logpass;
    Button home;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        logname = findViewById(R.id.logname);
        logpass = findViewById(R.id.logpassword);
        home = findViewById(R.id.next);
        DB= new DBHelper(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=logname.getText().toString();
                String passw=logpass.getText().toString();
                Boolean checkpass=DB.checkpass(user,passw);
                if(checkpass==true){
                    Toast.makeText(LoginPage.this,"Login successfully",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(LoginPage.this, HomePage.class);
                    startActivity(i);
                }

            }
        });
    }
}