package com.app.smartlibhost.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.smartlibhost.Fragment.fragment_addinfor1;
import com.app.smartlibhost.Fragment.fragment_addinfor2;

public class DialogAdapterrr extends FragmentStatePagerAdapter {
    public DialogAdapterrr(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new fragment_addinfor1();
            case 1: return new fragment_addinfor2();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
