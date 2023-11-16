package com.app.smartlibhost.activity.TheloaiActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.Sach;

import java.util.ArrayList;

import static com.app.smartlibhost.Fragment.fragment_scrollview.ms1;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms2;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms3;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms4;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms5;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms6;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms7;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms8;
import static com.app.smartlibhost.Fragment.fragment_scrollview.ms9;

public class TheLoai extends AppCompatActivity {
    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9;
    Toolbar theloai_toolbar;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    View v1;
    ArrayList<Sach> arrayList,array1,array2,array3,array4,array5,array6, array7, array8,array9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);


        Anhxa();
        ActionToolbar();

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                                in.putParcelableArrayListExtra("array",ms1);
                                in.putExtra("img",R.drawable.cartoon);
                                in.putExtra("title","Sách Thiếu Nhi");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms2);
                in.putExtra("img",R.drawable.giaotrinh);
                in.putExtra("title","Giáo trình");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms3);
                in.putExtra("img",R.drawable.policy);
                in.putExtra("title","Chính trị - Pháp Luật");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms4);
                in.putExtra("img",R.drawable.history);
                in.putExtra("title","Văn Hóa - Xã Hội - Lịch Sử");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms5);
                in.putExtra("img",R.drawable.story);
                in.putExtra("title","Truyện - Tiểu thuyết");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms6);
                in.putExtra("img",R.drawable.economic);
                in.putExtra("title","Khoa học - Kinh tế");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms7);
                in.putExtra("img",R.drawable.art);
                in.putExtra("title","Văn học - Nghệ thuật");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms8);
                in.putExtra("img",R.drawable.religion);
                in.putExtra("title","Tâm linh - Tôn giáo");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });
        cv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Pair<View, String> p3 = Pair.create(, "text");

                Intent in = new Intent(getApplicationContext(),TheLoai_Item.class);
                in.putParcelableArrayListExtra("array",ms9);
                in.putExtra("img",R.drawable.life);
                in.putExtra("title","Đời sống");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


            }
        });






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
        v1 = (View) findViewById(R.id.v1);
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
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);
        img8 = (ImageView) findViewById(R.id.img8);
        img9 = (ImageView) findViewById(R.id.img9);


    }

}
