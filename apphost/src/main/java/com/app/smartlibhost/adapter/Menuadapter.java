package com.app.smartlibhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.Menu_main;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Menuadapter extends BaseAdapter {

    public Menuadapter(ArrayList<Menu_main> menu_array, int dong_lvmenu, Context context) {
        this.menu_array = menu_array;
        this.context = context;
        this.layout = dong_lvmenu;
    }

    ArrayList<Menu_main> menu_array;
    Context context;
    int layout;

    @Override
    public int getCount() {
        return menu_array.size();
    }

    @Override
    public Object getItem(int position) {
        return menu_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Viewholder {
        ImageView img_menu;
        TextView ten_menu;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder viewholder;

            if (convertView==null) {
                viewholder = new Viewholder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout,null);
                viewholder.img_menu = (ImageView) convertView.findViewById(R.id.img_menu);
                viewholder.ten_menu = (TextView) convertView.findViewById(R.id.ten_menu);
                convertView.setTag(viewholder);

            } else
            {
                viewholder = (Viewholder) convertView.getTag();
            }

            Menu_main menu_main = menu_array.get(position);

            viewholder.ten_menu.setText(menu_main.getTen_menu());

            Picasso.get().load(menu_main.getImg_menu())
                    .placeholder(R.drawable.no_img)
                    .error(R.drawable.no_img)
                    .into(viewholder.img_menu);






        return convertView;
    }
}
