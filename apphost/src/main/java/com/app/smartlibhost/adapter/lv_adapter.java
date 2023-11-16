package com.app.smartlibhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.SachFB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class lv_adapter extends BaseAdapter implements Filterable {
    Context context;
    int layout;
  private List<SachFB> mangsach;
   private List<SachFB> mangsachoriginal;
   public static SachFB sach2;




    public lv_adapter(Context context, int layout, List<SachFB> mangsach) {
        this.context = context;
        this.layout = layout;
        this.mangsach = mangsach;
        this.mangsachoriginal = mangsach;
    }

    @Override
    public int getCount() {
        return mangsach.size() ;
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
        ImageView img_sach;
        TextView tensach,tentacgia,slconlai;
        ImageButton delete;



     }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.img_sach = (ImageView) convertView.findViewById(R.id.img_sach);
            viewHolder.tensach = (TextView) convertView.findViewById(R.id.tensach);
            viewHolder.tentacgia = (TextView) convertView.findViewById(R.id.tacgia);
            viewHolder.slconlai = (TextView) convertView.findViewById(R.id.conlai);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete);


            convertView.setTag(viewHolder);


        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        sach2 = mangsach.get(position);
        viewHolder.tensach.setText(sach2.getTensach());
        Picasso.get().load(sach2.getImg_sach())
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                .into(viewHolder.img_sach);
        viewHolder.tentacgia.setText("Tác giả: "+sach2.getTentacgia());

        viewHolder.slconlai.setText("SL: :"+sach2.getSoluong()+" Còn lại: "+sach2.getConlai());


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                viewHolder.delete.setVisibility(View.VISIBLE);

                return false;
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in2);
        convertView.startAnimation(animation);

        return convertView;

    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mangsach = mangsachoriginal;
                } else {
                    List<SachFB> filteredList = new ArrayList<>();
                    for (SachFB row : mangsachoriginal) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTensach().toLowerCase().contains(charString.toLowerCase()) || row.getTentacgia().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mangsach = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mangsach;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mangsach = (ArrayList<SachFB>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


}
