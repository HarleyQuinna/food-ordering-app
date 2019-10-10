package com.example.sylviachen.alexandrostakeout;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtFirst = (EditText) findViewById(R.id.txtFirst);
        final EditText txtLast = (EditText) findViewById(R.id.txtLast);
        final EditText txtAddress = (EditText) findViewById(R.id.txtAddress);
        final EditText txtCity = (EditText) findViewById(R.id.txtCity);
        final EditText txtPostal = (EditText) findViewById(R.id.txtPostal);

        final DatabaseManager dbM = new DatabaseManager(this);

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                String userName = myPref.getString("userName", "");
                String email = txtEmail.getText().toString();
                String first_name = txtFirst.getText().toString();
                String last_name = txtLast.getText().toString();
                String address = txtAddress.getText().toString();
                String city = txtCity.getText().toString();
                String postal = txtPostal.getText().toString();

                ContentValues cv = new ContentValues();
                String[] fields = {"user_name", "email_id", "first_name", "last_name", "address", "city", "postal_code"};
                String[] record = {userName, email, first_name, last_name, address, city, postal};
                dbM.updateRecord(cv, "tb_user", fields, record);

                Intent intent = new Intent(UpdateActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
