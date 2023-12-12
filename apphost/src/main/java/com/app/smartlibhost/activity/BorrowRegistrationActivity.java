package com.app.smartlibhost.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.smartlibhost.R;
import com.app.smartlibhost.fragment.BorrowRegistrationFragment;

public class BorrowRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_registration);

        // Load BorrowRegistrationFragment into the activity
        loadFragment();
    }

    private void loadFragment() {
        BorrowRegistrationFragment borrowRegistrationFragment = new BorrowRegistrationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, borrowRegistrationFragment);
        fragmentTransaction.commit();
    }
}
