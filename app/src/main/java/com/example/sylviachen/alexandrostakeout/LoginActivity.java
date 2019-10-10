package com.example.sylviachen.alexandrostakeout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

        final TextView userValidate = (TextView) findViewById(R.id.userValidate);
        final TextView passwordValidate = (TextView) findViewById(R.id.passwordValidate);

        final DatabaseManager dbM = new DatabaseManager(this);
        final SQLiteDatabase db = dbM.getReadableDatabase();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences myPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = myPref.edit();

                    String[] columns = {"user_name", "password"};
                    String selection = "user_name" + " = ?";
                    String[] selectionArgs = {txtUsername.getText().toString()};
                    Cursor cursor = db.query("tb_user",
                            columns, selection, selectionArgs, null, null, null);
                    if(cursor.getCount() == 0)
                    {
                        userValidate.setText(getResources().getString(R.string.username_login));
                    }
                    else if(cursor.getCount() > 0)
                    {
                        cursor.moveToFirst();
                        if(!txtPassword.getText().toString().equals(cursor.getString(1)))
                        {
                            passwordValidate.setText(getResources().getString(R.string.pw_login));
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            String userName = txtUsername.getText().toString();
                            String isLoggedin = "true";
                            prefEditor.putString("userName", userName);
                            prefEditor.putString("isLoggedin", isLoggedin);
                            prefEditor.apply();
                        }
                    }
                }
        });
    }

    //action for sign up button
    public void registerAccount(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
