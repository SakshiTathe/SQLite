package com.example.sqlitedb;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText name, email, phone, age, date,pass;
    CheckBox term;
    RadioButton male, female;
    Button submit,login;
    RadioGroup genderRadioGroup;

    DBHelper DB;

    boolean isallfieldcheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.stname);
        pass = findViewById(R.id.stpassword);
        email = findViewById(R.id.stemail);
        phone = findViewById(R.id.stphone);
        age = findViewById(R.id.stdage);
        date = findViewById(R.id.stdbod);
        term = findViewById(R.id.checkBox);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        submit = findViewById(R.id.sub);
        login = findViewById(R.id.llogin);
        genderRadioGroup=findViewById(R.id.gender_radiogroup);
        DB=new DBHelper(MainActivity.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isallfieldcheck = CheckAllFields();
                if(isallfieldcheck){
                    String user=name.getText().toString();
                    String passw=pass.getText().toString();
                    Boolean checkuse=DB.checkuser(user);
                    if(checkuse==false){
                        boolean insert=DB.insertdata(user,passw);
                        if(insert){
                            Toast.makeText(MainActivity.this,"Register successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(MainActivity.this, HomePage.class);
                            startActivity(i);
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"user exit",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(MainActivity.this, LoginPage.class);
                        startActivity(i);
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, LoginPage.class);
                startActivity(i);
            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
                }
            });
        }

    private boolean CheckAllFields() {
        String emaill=email.getText().toString().trim();
        String checkemail="[a-zA-Z0-9+_.-]+@[a-z]+\\\\.[a-z]{2,3}";
        String phoneRegex = "\\d{10}";

        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }
        if (emaill.matches(checkemail)) {
            email.setError("Invalid Email");
            return false;
        }
        if(!term.isChecked()){
            Toast.makeText(this, "You must accept the terms and conditions", Toast.LENGTH_LONG).show();
            return false;
        }
        if (genderRadioGroup.getCheckedRadioButtonId()==-1) {
            Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if (date.length()==0) {
            date.setError("Enter a valid date (YYYY-MM-DD)");
            return false;
        }
        if (!phone.getText().toString().matches(phoneRegex)) {
            phone.setError("Phone is required");
            return false;
        } else if (pass.length() < 6 || pass.length() > 12) {
            pass.setError("Password must be between 6-12 characters ");
            return false;
        }
        return true;
    }
    /*
     RadioButton selectedRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());
                gender = selectedRadioButton == null ? "" : selectedRadioButton.getText().toString().trim();
            */
}