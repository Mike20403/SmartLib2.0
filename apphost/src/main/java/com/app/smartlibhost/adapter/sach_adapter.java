package com.app.smartlibhost.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.smartlibhost.R;

import com.app.smartlibhost.activity.BookDetailsActivity;
import com.app.smartlibhost.model.SachFB;
import com.app.smartlibhost.ultil.CheckConnection;
import com.app.smartlibhost.ultil.server;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class sach_adapter extends RecyclerView.Adapter<sach_adapter.item_holder> {
    Context context;
    ArrayList<SachFB> arraysach;
    SachFB sach;



    public sach_adapter(ArrayList<SachFB> arraysach, Context context) {
        this.context = context;
        this.arraysach = arraysach;
    }



    @NonNull
    @Override
    public item_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_recycle,null);
       item_holder item_holder = new item_holder(view);

        return item_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull item_holder item_holder, int i) {

            sach = arraysach.get(i);
            item_holder.tensach.setText(sach.getTensach());
            Picasso.get().load(sach.getImg_sach())
                    .placeholder(R.drawable.no_img)
                    .error(R.drawable.no_img)
                    .into(item_holder.img_sach);
            item_holder.tacgia.setText("Tác giả: "+sach.getTentacgia());
            item_holder.slconlai.setText("SL: :"+sach.getSoluong()+" Còn lại: "+sach.getConlai());

    }

    @Override
    public int getItemCount() {

        return arraysach.size();
    }

    public class item_holder extends RecyclerView.ViewHolder{
        public ImageView img_sach;
        public TextView tensach,tacgia,slconlai;
        public ImageButton delete;




        public item_holder(final View itemView) {
            super(itemView);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (arraysach != null){
                         final String id_sach = arraysach.get(getPosition()).getId_sach();
                         final int luotclick = 1;
                         Intent intent = new Intent(context, BookDetailsActivity.class);
                         intent.putExtra("thongtinsach", (Serializable) arraysach.get(getPosition()));
                         CheckConnection.ShowToast_short(context,arraysach.get(getPosition()).getTensach());

                         RequestQueue requestQueue = Volley.newRequestQueue(context);

                         StringRequest stringRequest = new StringRequest(Request.Method.POST, server.luotclick_url,
                                 new Response.Listener<String>() {
                                     @Override
                                     public void onResponse(String response) {
                                         Log.d("AAA",response);
                                     }
                                 }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {

                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() throws AuthFailureError {
                                 HashMap<String,String> hashMap = new HashMap<>();
                                 hashMap.put("id_sach",""+id_sach);
                                 hashMap.put("luotclick",""+luotclick);
                                 return hashMap;
                             }
                         };


                         requestQueue.add(stringRequest);
                         context.startActivity(intent);


                     }



                 }
             });

            img_sach = (ImageView) itemView.findViewById(R.id.img_sach);
            tensach = (TextView) itemView.findViewById(R.id.tensach);
            tacgia = (TextView) itemView.findViewById(R.id.tacgia);
            slconlai = (TextView) itemView.findViewById(R.id.conlai);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_from_bottom);
            itemView.startAnimation(animation);

        }
    }
}
