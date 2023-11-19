package com.app.smartlibhost.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.smartlibhost.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BorrowReturnActivity extends AppCompatActivity {

    private EditText bookTitleEditText, borrowerNameEditText;
    private Button borrowButton, returnButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_return);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bookTitleEditText = findViewById(R.id.editTextBookTitle);
        borrowerNameEditText = findViewById(R.id.editTextBorrowerName);
        borrowButton = findViewById(R.id.buttonBorrow);
        returnButton = findViewById(R.id.buttonReturn);

        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowBook();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnBook();
            }
        });
    }

    private void borrowBook() {
        String bookTitle = bookTitleEditText.getText().toString().trim();
        String borrowerName = borrowerNameEditText.getText().toString().trim();

        if (bookTitle.isEmpty() || borrowerName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference borrowRef = mDatabase.child("borrows").child(userId).push();

        borrowRef.child("bookTitle").setValue(bookTitle);
        borrowRef.child("borrowerName").setValue(borrowerName);
        borrowRef.child("status").setValue("Borrowed");

        Toast.makeText(this, "Đã mượn sách", Toast.LENGTH_SHORT).show();
        clearInputFields();
    }

    private void returnBook() {
        String bookTitle = bookTitleEditText.getText().toString().trim();
        String borrowerName = borrowerNameEditText.getText().toString().trim();

        if (bookTitle.isEmpty() || borrowerName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference borrowRef = mDatabase.child("borrows").child(userId);

        borrowRef.child("status").setValue("Returned");

        Toast.makeText(this, "Đã trả sách", Toast.LENGTH_SHORT).show();
        clearInputFields();
    }

    private void clearInputFields() {
        bookTitleEditText.setText("");
        borrowerNameEditText.setText("");
    }
}
