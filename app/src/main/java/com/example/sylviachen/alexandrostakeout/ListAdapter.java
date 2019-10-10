package com.example.sylviachen.alexandrostakeout;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<AccountItem> {
    private static final String TAG= "ListAdapter";
    private Context mContext;
    private int mResource;
    private  int lastPosition = -1;
    static class ViewHolder{
        TextView name;
        ImageView img;

    }

    public ListAdapter(Context context, int resource, ArrayList<AccountItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        Bitmap bitmap = getItem(position).getBitmap();

        AccountItem items = new AccountItem(name, bitmap);

        //holder for anim

        ListAdapter.ViewHolder holder;

        //show anim
        final View result;



        holder = new ListAdapter.ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.textname);
            holder.img= (ImageView) convertView.findViewById(R.id.imgIcon);
            result = convertView;
            convertView.setTag(holder);

        }
        else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }




        /*Animation animation = AnimationUtils.loadAnimation(mContext,(position > lastPosition)
                ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);*/
        lastPosition = position;

        holder.name.setText(name);
        holder.img.setImageBitmap(bitmap);


        return convertView;

    }

}
