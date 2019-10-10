package com.example.sylviachen.alexandrostakeout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends ArrayAdapter<Items> {

    private static final String TAG= "ItemListAdapter";
    private Context mContext;
    private int mResource;
    private  int lastPosition = -1;
    static class ViewHolder{
        TextView name;
        TextView price;

    }

    public ItemListAdapter(Context context, int resource, ArrayList<Items> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        String name = getItem(position).getName();
        String price = getItem(position).getPrice();

        Items items = new Items(name,price);

        //holder for anim

        ViewHolder holder;

        //show anim
        final View result;



        holder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.textname);
            holder.price = (TextView) convertView.findViewById(R.id.textPrice);
            result = convertView;
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }




        Animation animation = AnimationUtils.loadAnimation(mContext,(position > lastPosition)
        ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(name);
        holder.price.setText(price);


        return convertView;

    }
}
