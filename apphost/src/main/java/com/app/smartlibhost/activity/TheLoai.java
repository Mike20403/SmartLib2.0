package com.app.smartlibhost.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.app.smartlibhost.R;

public class TheLoai extends AppCompatActivity {
    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9;
    Toolbar theloai_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);


        Anhxa();
        ActionToolbar();



    }
   
    private void ActionToolbar() {
        setSupportActionBar(theloai_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        theloai_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void Anhxa() {
         theloai_toolbar = findViewById(R.id.toolbar);
        cv1 = (CardView) findViewById(R.id.cardView1);
        cv2 = (CardView) findViewById(R.id.cardView2);
        cv3 = (CardView) findViewById(R.id.cardView3);
        cv4 = (CardView) findViewById(R.id.cardView4);
        cv5 = (CardView) findViewById(R.id.cardView5);
        cv6 = (CardView) findViewById(R.id.cardView6);
        cv7 = (CardView) findViewById(R.id.cardView7);
        cv8 = (CardView) findViewById(R.id.cardView8);
        cv9 = (CardView) findViewById(R.id.cardView9);

    }

}
