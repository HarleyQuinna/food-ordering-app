package com.example.sylviachen.alexandrostakeout;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager viewPager;
    TabLayout indicator;
    ImageView gyroPic,souvlakiPic,burgerPic,friesPic,drinkPic;

    List<Bitmap> bitmaps;
    //private static Context context;
   Context mContext;

    private static final int PERMISSION_REQUEST_CODE = 1;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    IntentFilter intentFilter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        indicator = (TabLayout)view.findViewById(R.id.indicator);

        bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.chicken));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.alexandros));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.tmg));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.shrimp));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.souvlaki));

        viewPager.setAdapter(new SliderAdapter(getContext(), bitmaps));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        sentPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(SENT), 0);

        deliveredPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(DELIVERED), 0);

        //intent to filter the action for SMS messages received
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");


        Button btnSMS = (Button) view.findViewById(R.id.btnSms);
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this code works for 4.4 or higher versions
                Uri uri = Uri.parse("smsto:6477667666");
                //Intent it = new Intent(Intent.ACTION_SENDTO, uri);

                //
                Intent i = new Intent(android.content.Intent.ACTION_SENDTO,uri);
                //i.putExtra("address", "5556");

                i.putExtra("sms_body", "Hi Alexandros, I'm wondering...");
                startActivity(i);
            }
        });

        gyroPic = (ImageView) view.findViewById(R.id.gyros);
        gyroPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gyrosIntent = new Intent(getActivity(), com.example.sylviachen.alexandrostakeout.gyros.class);
                startActivity(gyrosIntent);
            }
        });


        souvlakiPic = (ImageView) view.findViewById(R.id.souvlaki);
        souvlakiPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent souvlakiIntent = new Intent(getActivity(), Souvlaki.class);
                startActivity(souvlakiIntent);
            }
        });

        burgerPic = (ImageView) view.findViewById(R.id.burger);
        burgerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent burgerIntent = new Intent(getActivity(), Burger.class);
                startActivity(burgerIntent);
            }
        });

        friesPic = (ImageView) view.findViewById(R.id.fries);
        friesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friesIntent = new Intent(getActivity(),Fries.class);
                startActivity(friesIntent);
            }
        });

        drinkPic = (ImageView) view.findViewById(R.id.drink);
        drinkPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drinkIntent = new Intent(getActivity(),Drinks.class);
                startActivity(drinkIntent);
            }
        });

        //WEB VIEW
        Button btnWeb = (Button)view.findViewById(R.id.btnWeb);
        Button btnWeb2 = (Button)view.findViewById(R.id.btnWeb2);


        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                startActivity(intent);
            }
        });

        btnWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NetworkDemo.class);
                startActivity(intent);
            }
        });
        // google map
        Button btnMap=(Button)view.findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MapsActivity.class);

                startActivity(intent);
            }
        });

        return view;

    }

    protected IActivityEnabledListener aeListener;

    protected interface IActivityEnabledListener{
        void onActivityEnabled(FragmentActivity activity);
    }

    protected void getAvailableActivity(IActivityEnabledListener listener){
        if (getActivity() == null){
            aeListener = listener;

        } else {
            listener.onActivityEnabled(getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) activity);
            aeListener = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) context);
            aeListener = null;
        }
    }


   private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(getActivity()== null)
            {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < bitmaps.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
