package com.app.smartlibhost.other;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.smartlibhost.Fragment.fragment_tracuu;


public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private int size;
    private fragment_tracuu fragment_tracuu;
    Fragment fragment;

    public MyViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {


        return AFragment.newInstance(position+"");

    }
    @Override
    public int getCount() {
        return size;
    }
}
