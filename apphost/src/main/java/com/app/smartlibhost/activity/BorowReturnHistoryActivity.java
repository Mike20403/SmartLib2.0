package com.app.smartlibhost.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.HistoryAdapter;
import com.app.smartlibhost.model.Borrow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BorowReturnHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistory;
    private TextView textViewEmpty;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private List<Borrow> borrowList;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        textViewEmpty = findViewById(R.id.textViewEmpty);

        borrowList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(borrowList);

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(historyAdapter);

        loadBorrowHistory();

        // Add back button to the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadBorrowHistory() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference borrowRef = mDatabase.child("borrows").child(userId);

            borrowRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    borrowList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Borrow borrow = snapshot.getValue(Borrow.class);
                        if (borrow != null) {
                            borrowList.add(borrow);
                        }
                    }

                    historyAdapter.notifyDataSetChanged();
                    updateEmptyView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        }
    }

    private void updateEmptyView() {
        if (borrowList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewHistory.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
