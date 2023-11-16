package com.app.smartlibhost.other;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.smartlibhost.R;


public class AFragment extends Fragment {
    private static final String ARG_C = "content";
    View view;



    public static AFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String content = getArguments().getString(ARG_C);

       switch (content) {
           case "0":
               view = inflater.inflate(R.layout.fragment_addsach,container,false);





               break;
           case "1":
               view = inflater.inflate(R.layout.fragment_addsl,container,false);
               break;
               default:


       }



        return view;
    }













}
