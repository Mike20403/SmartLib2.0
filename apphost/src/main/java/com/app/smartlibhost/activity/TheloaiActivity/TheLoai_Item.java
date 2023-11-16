package com.app.smartlibhost.activity.TheloaiActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;

import com.app.smartlibhost.model.SachFB;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.sachadapter;

import java.util.ArrayList;


public class TheLoai_Item extends AppCompatActivity {
    Toolbar thieunhi_toolbar;
    ImageView img;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ArrayList<SachFB> array;
    int image;
    String title;
    RecyclerView rv;
    sachadapter sach_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thieu_nhi);

        GetIntent();
        Anhxa();




        ActionToolbar();


    }

    private void GetIntent() {
        Intent intent = getIntent();
        array = intent.getParcelableArrayListExtra("array");
        image = intent.getIntExtra("img",R.drawable.cartoon);

        title = intent.getStringExtra("title");
    }

    private void ActionToolbar() {
        setSupportActionBar(thieunhi_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        thieunhi_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        collapsingToolbarLayout.setTitle(title);

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);






    }


    private void Anhxa() {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        img = (ImageView) findViewById(R.id.image);
        thieunhi_toolbar = (Toolbar) findViewById(R.id.thieunhi_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        rv = (RecyclerView) findViewById(R.id.rvsach);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        //OverScrollDecoratorHelper.setUpOverScroll(rv, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        rv.setHasFixedSize(true);
        sach_adapter = new sachadapter(this,array);
        rv.setAdapter(sach_adapter);
        sach_adapter.notifyDataSetChanged();
        img.setImageResource(image);


    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
           /* if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            } */
        }
    }
}


