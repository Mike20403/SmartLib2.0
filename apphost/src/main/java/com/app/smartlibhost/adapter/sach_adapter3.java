package com.app.smartlibhost.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.TextView;

import com.app.smartlibhost.R;

import com.app.smartlibhost.model.Sach2;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;


public  class sach_adapter3 extends RecyclerView.Adapter<sach_adapter3.item_holder> {
            Context context;

    private SparseBooleanArray selectedItems;



    public sach_adapter3(Context context, ArrayList<Sach2> arraysach) {
        this.context = context;
        this.arraysach = arraysach;


    }

    public static ArrayList<Sach2> arraysach;
    Sach2 sach;


    @NonNull
    @Override
    public item_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_recycle3,null);
        item_holder item_holder = new item_holder(view);

        return item_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final item_holder item_holder, int i) {
        sach = arraysach.get(i);
        item_holder.tensach.setText(sach.getTensach());
        Picasso.get().load(sach.getImg_sach())
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                .into(item_holder.img_sach);
        item_holder.tacgia.setText("Tác giả: "+sach.getTentacgia());
        item_holder.slconlai.setText("SL: :"+sach.getSoluong()+" Còn lại: "+sach.getConlai());
        item_holder.checkBox.setChecked(arraysach.get(i).getCheckedstate());


        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        item_holder.checkBox.setTag(i);
        item_holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos= (Integer) item_holder.checkBox.getTag();


                if (arraysach.get(pos).getCheckedstate()) {
                    arraysach.get(pos).setCheckedstate(false);
                } else {
                    arraysach.get(pos).setCheckedstate(true);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return arraysach.size();
    }


            public class item_holder extends RecyclerView.ViewHolder{
        public ImageView img_sach;
        public TextView tensach,tacgia,slconlai;
        protected  CheckBox checkBox;


        public item_holder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer pos= (Integer) checkBox.getTag();

                    if (checkBox.isChecked()){
                      checkBox.setChecked(false);
                        arraysach.get(pos).setCheckedstate(false);

                  } else {
                      checkBox.setChecked(true);
                        arraysach.get(pos).setCheckedstate(true);

                  }
                }
            });

            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            img_sach = (ImageView) itemView.findViewById(R.id.img_sach);
            tensach = (TextView) itemView.findViewById(R.id.tensach);
            tacgia = (TextView) itemView.findViewById(R.id.tacgia);
            slconlai = (TextView) itemView.findViewById(R.id.conlai);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_from_bottom);
            itemView.startAnimation(animation);

        }
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }
}
