package com.app.smartlibhost.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.smartlibhost.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_addinfor1 extends Fragment {
    View view;
    static EditText sdt,address,job;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addinfo1,container,false);

        sdt = (EditText) view.findViewById(R.id.sdtedt);
        address = (EditText) view.findViewById(R.id.diachiedt);
        job = (EditText) view.findViewById(R.id.jobedt);




        return view;
    }

    public static boolean Checkedt(){
        if (sdt.getText().length() >0 && address.getText().length()>0 && job.getText().length()>0){



                        return true;

        } else {return  false;}


    }

    public static String Getsdt() {
        return  sdt.getText().toString();
    }
    public static String GetAddress(){
        return  address.getText().toString();
    }
    public static String Getjob(){
        return job.getText().toString();
    }

}
