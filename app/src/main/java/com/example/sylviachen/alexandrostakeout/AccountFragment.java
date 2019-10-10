package com.example.sylviachen.alexandrostakeout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        // Inflate the layout for this fragment
        SharedPreferences myPref = getActivity().getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        final SharedPreferences.Editor prefEditor = myPref.edit();
        final String user = myPref.getString("userName","");

        ImageView imgAccount = (ImageView) view.findViewById(R.id.imgAccount);
        TextView txtUser = (TextView) view.findViewById(R.id.txtUser);

        if(! user.equals(""))
        {
            imgAccount.setImageResource(R.drawable.aava);
            txtUser.setText(user);
        }
        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! user.equals(""))
                {
                    Intent intent = new Intent(getActivity(), UpdateActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! user.equals(""))
                {
                    Intent intent = new Intent(getActivity(), UpdateActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        Log.d(TAG,"onCreate: Started.");
        ListView mListView = (ListView) view.findViewById(R.id.accountList);

        AccountItem manage = new AccountItem("Manage Account Information",BitmapFactory.decodeResource(getResources(), R.drawable.acc));
        AccountItem history = new AccountItem("Order History",BitmapFactory.decodeResource(getResources(), R.drawable.history));
        AccountItem location = new AccountItem("My Delivery Address",BitmapFactory.decodeResource(getResources(), R.drawable.llo));
        AccountItem logout = new AccountItem("Log Out",BitmapFactory.decodeResource(getResources(), R.drawable.logout));

        ArrayList<AccountItem> itemList = new ArrayList<>();
        itemList.add(manage);
        itemList.add(history);
        itemList.add(location);
        itemList.add(logout);

        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.account_list,itemList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = null;
                switch(position)
                {
                    case 0:
                        if(user.equals(""))
                        {
                            intent = new Intent(getActivity(), LoginActivity.class);
                            break;
                        }
                        else{
                            intent = new Intent(getActivity(), PersonalInfoActivity.class);
                            break;
                        }

                    case 1:
                        intent = new Intent(getActivity(), OrderHistoryActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), ChangeAddressActivity.class);
                        break;
                    case 3:
                        prefEditor.putString("userName", "");
                        prefEditor.apply();
                        intent = new Intent(getActivity(), LoginActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        return view;
    }
}
