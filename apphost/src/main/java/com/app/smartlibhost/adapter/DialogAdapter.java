package com.app.smartlibhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.SachFB;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder>{
    Context context;
   ArrayList<SachFB> sacharray;
   String dateborrowed,datepayback;

    public DialogAdapter(Context context, ArrayList<SachFB> sacharray, String dateborrowed, String datepayback) {
        this.context = context;
        this.sacharray = sacharray;
        this.dateborrowed = dateborrowed;
        this.datepayback = datepayback;
    }

    public DialogAdapter(FragmentManager manager) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dongdialog,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SachFB sach = sacharray.get(position);

        holder.datepayback.setText(datepayback);
        holder.name.setText(sach.getTensach());
        holder.dateborrow.setText(dateborrowed);
        holder.price.setText("50.000VND");

    }

    @Override
    public int getItemCount() {
        return sacharray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,dateborrow,datepayback,price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            dateborrow = (TextView) itemView.findViewById(R.id.borrowdate);
            datepayback = (TextView) itemView.findViewById(R.id.paybackdate);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }

}
