package com.example.sylviachen.alexandrostakeout;

import android.content.ContentValues;
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

public class RegisterActivity extends AppCompatActivity {

  private static final String tables[]={"tb_user","tb_menu", "tb_order"};
    //
    private static final String tableCreator[] =
            {"CREATE TABLE tb_user (user_id INTEGER PRIMARY KEY AUTOINCREMENT , user_name TEXT, " +
                    "password TEXT, phone Text, first_name TEXT, last_name TEXT, email_id TEXT, address TEXT, city TEXT, postal_code TEXT);",
                    "CREATE TABLE tb_menu (food_id INTEGER PRIMARY KEY AUTOINCREMENT , food_name TEXT, " +
                            "price DECIMAL, category TEXT);",
                    "CREATE TABLE tb_order (order_id INTEGER PRIMARY KEY AUTOINCREMENT , phone TEXT, " +
                            "amount_paid DECIMAL, order_status TEXT);"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText txtUser = (EditText)findViewById(R.id.txtUser);
        final EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
        final EditText txtFirst = (EditText)findViewById(R.id.txtFirst);
        final EditText txtLast = (EditText)findViewById(R.id.txtLast);
        final EditText txtAddress = (EditText)findViewById(R.id.txtAddress);
        final EditText txtCity = (EditText)findViewById(R.id.txtCity);
        final EditText txtPostal = (EditText)findViewById(R.id.txtPostal);
        final EditText txtPw = (EditText)findViewById(R.id.txtPassword1);
        final EditText txtPwCon = (EditText)findViewById(R.id.txtPassword2);
        final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);

        final TextView userValidate = (TextView)findViewById(R.id.userValidate);
        final TextView emailValidate = (TextView)findViewById(R.id.emailValidate);
        final TextView firstValidate = (TextView)findViewById(R.id.firstValidate);
        final TextView lastValidate = (TextView)findViewById(R.id.lastValidate);
        final TextView pwValidate = (TextView)findViewById(R.id.pwValidate);
        final TextView pwconValidate = (TextView)findViewById(R.id.pwconValidate);
        final TextView phoneValidate = (TextView) findViewById(R.id.phoneValidate);

        //define email and password pattern
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";


        Button btnRegister = findViewById(R.id.btnRegister);

       final DatabaseManager db = new DatabaseManager(this);
        //db.createDatabase(getApplicationContext());
        db.dbInitialize(tables,tableCreator);

        final String user_fields[] = {"user_id", "user_name", "password", "phone", "first_name", "last_name", "email_id", "address", "city", "postal_code"};
        final String record[] = new String[10];
        final String record1[] = new String[4];

        final String menu_fields[] = {"food_id",  "food_name", "price", "category"};
        final String order_fields[] = {"order_id", "phone", "amount_paid", "order_status"};

        final SQLiteDatabase database = db.getReadableDatabase();

        //action for submit button
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                record[1] = txtUser.getText().toString();
                record[2] = txtPw.getText().toString();
                record[3] = txtPhone.getText().toString();
                record[4] = txtFirst.getText().toString();
                record[5] = txtLast.getText().toString();
                record[6] = txtEmail.getText().toString();
                record[7] = txtAddress.getText().toString();
                record[8] = txtCity.getText().toString();
                record[9] = txtPostal.getText().toString();

                    String[] column_name = {"user_name"};
                    final String[] column_email = {"email_id"};
                    String[] column_phone = {"phone"};
                    String selection_name = "user_name" + " = ?";
                    String selection_email = "email_id" + " = ?";
                    String selection_phone = "phone" + " = ?";
                    String[] selectionArgs_name = {txtUser.getText().toString()};
                    String[] selectionArgs_email = {txtEmail.getText().toString()};
                    String[] selectionArgs_phone = {txtPhone.getText().toString()};
                    final Cursor cursor_name = database.query("tb_user",
                            column_name, selection_name, selectionArgs_name, null, null, null);

                    final Cursor cursor_email = database.query("tb_user",
                            column_email, selection_email, selectionArgs_email, null, null, null);

                    final Cursor cursor_phone = database.query("tb_user",
                            column_phone, selection_phone, selectionArgs_phone, null, null, null);

                    //validation for registration input
                    boolean isValid = true;
                    if(txtUser.getText().toString().contains(" ") || txtUser.getText().toString().equals(""))
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_validation));
                    }
                    if(cursor_name.getCount() > 0)
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_repeat));
                    }
                    if(! txtEmail.getText().toString().trim().matches(emailPattern) || txtEmail.getText().toString().equals(""))
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_validation));
                    }
                    if(cursor_email.getCount() > 0)
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_repeat));
                    }
                if(txtPhone.getText().toString().length() != 10|| txtPhone.getText().toString().equals(""))
                {
                    isValid = false;
                    phoneValidate.setText(getResources().getString(R.string.phone_validation));
                }
                if(cursor_phone.getCount() > 0)
                {
                    isValid = false;
                    phoneValidate.setText(getResources().getString(R.string.phone_repeat));
                }
                    if(txtFirst.getText().toString().equals(""))
                    {
                        isValid = false;
                        firstValidate.setText(getResources().getString(R.string.first_validation));
                    }
                    if(txtLast.getText().toString().equals(""))
                    {
                        isValid = false;
                        lastValidate.setText(getResources().getString(R.string.last_validation));
                    }
                    if(! txtPw.getText().toString().matches(passwordPattern) || txtPw.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwValidate.setText(getResources().getString(R.string.pw_validation));
                    }
                    if(! txtPwCon.getText().toString().equals(txtPw.getText().toString()) || txtPwCon.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwconValidate.setText(getResources().getString(R.string.pwcon_validation));
                    }
                    if(isValid) {
                        Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);

                        //create shared preference to store the userName
                        SharedPreferences myPreference = getSharedPreferences("SharedId", 0);
                        SharedPreferences.Editor prefEditor = myPreference.edit();
                        prefEditor.putString("userName", record[1]);
                        prefEditor.putString("phone", record[3]);
                        prefEditor.apply();

                        startActivity(intent);
                        ContentValues values = new ContentValues();

                        //add the row to the database
                        db.addRecord(values, "tb_user", user_fields, record);
                        record1[1] = "Chicken Gyros on pita"; record1[2] = "7.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Pork Gyros in pita"; record1[2] = "7.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Gyros Pork on Greek Salad"; record1[2] = "13.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Gyros Chicken on Greek Salad"; record1[2] = "13.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Chicken Gyros on Greek Fries"; record1[2] = "13.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Pork Gyros on Greek Fries"; record1[2] = "13.00"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Chicken Gyros on a Box"; record1[2] = "8.50"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);

                        record1[1] = "Pork Gyros on a Box"; record1[2] = "8.50"; record[3] = "Gyros Chicken or Pork";
                        db.addRecord(values, "tb_menu", menu_fields, record1);
                    }
            }
        });

    }
}
