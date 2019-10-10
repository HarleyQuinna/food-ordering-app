package com.example.sylviachen.alexandrostakeout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PersonalInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        TextView txtUser = findViewById(R.id.txtUser);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtPassword = findViewById(R.id.txtPassword);
        TextView txtUserId = findViewById(R.id.txtUserId);
        TextView txtId = findViewById(R.id.txtId);
        TextView txtPhone = findViewById(R.id.txtPhone);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences myPrefer = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);

        final DatabaseManager dbM = new DatabaseManager(this);

        //retrieving from shared preferences
        String userName = myPrefer.getString("userName","");


        SQLiteDatabase db = dbM.getReadableDatabase();
        txtUser.setText(userName);

            String[] columns = {"user_id", "user_name", "password", "phone",
                    "first_name", "last_name", "email_id", "address", "city", "postal_code"};

            String selection = "user_name" + " = ?";
            String[] selectionArgs = {userName};
            Cursor cursor = db.query("tb_user",
                    columns, selection, selectionArgs, null, null, null);

            if(null != cursor)
            {
                cursor.moveToFirst();
                txtUserId.setText(cursor.getString(0));
                txtUser.setText(cursor.getString(1));
                txtPassword.setText(cursor.getString(2));
                txtPhone.setText(cursor.getString(3));
                String name = cursor.getString(4) + "  " + cursor.getString(5);
                txtName.setText(name);
                txtEmail.setText(cursor.getString(6));
                String address = cursor.getString(7) + ", "
                        + cursor.getString(8) +", " + cursor.getString(9);
                txtAddress.setText(address);
                cursor.close();
            }
            db.close();

        Button btnUpdate = (Button) findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

    }
}
