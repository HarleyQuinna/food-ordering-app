package com.example.sylviachen.alexandrostakeout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class gyros extends AppCompatActivity {

    private static final String TAG="gyros";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyros);

        Log.d(TAG,"onCreate: Started.");
        ListView mListView = (ListView)findViewById(R.id.gyrosListView);

        Items pitaCh = new Items("Chicken Gyros in a Pita","7.00 $");
        Items pitapr = new Items("Pork Gyros on a Pita","7.00 $");
        Items gyroGreekSalad = new Items("Gyros Chicken on a Greek Salad","13.00 $");
        Items gyrochGreekSalad = new Items("Gyros Pork on Greek Salad","13.00 $");
        Items gyrosgf = new Items("Chicken Gyros on Greek Fries","13.00 $");
        Items gyrosprgf = new Items("Pork Gyros on Greek Fries","13.00 $");
        Items gyrosChBox = new Items("Chicken Gyros on a Box","8.50 $");
        Items gyrosPr = new Items("Pork Gyros on a Box","8.50 $");


        ArrayList<Items> itemList = new ArrayList<>();
        itemList.add(pitaCh);
        itemList.add(pitapr);
        itemList.add(gyrochGreekSalad);
        itemList.add(gyroGreekSalad);
        itemList.add(gyrochGreekSalad);
        itemList.add(gyrosgf);
        itemList.add(gyrosprgf);
        itemList.add(gyrosChBox);
        itemList.add(gyrosPr);

        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.gyros_listview,itemList);
        mListView.setAdapter(adapter);


    }
}
