package com.app.smartlibhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Users_adapter extends BaseAdapter implements Filterable {

    Context context;
    int layout;
    private List<Users> manguser;


    private  List<Users> manguseroriginal;
    Users users;

    public Users_adapter(Context context, int layout, List<Users> manguser) {
        this.context = context;
        this.layout = layout;
        this.manguser = manguser;
        this.manguseroriginal = manguser;
    }




    @Override
    public int getCount() {
        return manguser.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView fb_img;
        TextView username,contact,userid;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.fb_img = (ImageView) convertView.findViewById(R.id.fb_img);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.userid = (TextView) convertView.findViewById(R.id.mathanhvien);
            viewHolder.contact = (TextView) convertView.findViewById(R.id.contact);



            convertView.setTag(viewHolder);


        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        users = manguser.get(position);
        viewHolder.username.setText(users.getName_user());
        Picasso.get().load(users.getFb_img())
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                .into(viewHolder.fb_img);

        viewHolder.userid.setText("Mã kiểm soát: "+users.getId_fb());

        viewHolder.contact.setText("Email: "+users.getEmail_user());





        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    manguser = manguseroriginal;
                } else {
                    List<Users> filteredList = new ArrayList<>();
                    for (Users row : manguseroriginal) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getId_fb().toLowerCase().contains(charString.toLowerCase()) || row.getName_user().contains(charSequence)|| row.getEmail_user().contains(charSequence)
                               ) {
                            filteredList.add(row);
                        }
                    }

                    manguser = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = manguser;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                manguser = (ArrayList<Users>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
