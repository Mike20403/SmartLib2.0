package com.app.smartlibhost.activity.TheloaiActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.smartlibhost.Fragment.fragment_scrollview;
import com.app.smartlibhost.R;
import com.app.smartlibhost.activity.TheloaiActivity.TheLoai_Item;
import com.app.smartlibhost.model.SachFB;

import java.util.ArrayList;
import java.util.Arrays;

public class TheLoai extends AppCompatActivity implements View.OnClickListener {
    private static final int[] CARD_VIEW_IDS = {
            R.id.cardView1, R.id.cardView2, R.id.cardView3,
            R.id.cardView4, R.id.cardView5, R.id.cardView6,
            R.id.cardView7, R.id.cardView8, R.id.cardView9
    };

    private static final int[] IMAGE_VIEW_IDS = {
            R.id.img1, R.id.img2, R.id.img3,
            R.id.img4, R.id.img5, R.id.img6,
            R.id.img7, R.id.img8, R.id.img9
    };

    private static final int[] DRAWABLE_IDS = {
            R.drawable.cartoon, R.drawable.giaotrinh, R.drawable.policy,
            R.drawable.history, R.drawable.story, R.drawable.economic,
            R.drawable.art, R.drawable.religion, R.drawable.life
    };


    private static final int[] TITLE_IDS = {
            R.string.title_sach_thieunhi, R.string.title_giaotrinh, R.string.title_chinhtri,
            R.string.title_vanhoc, R.string.title_truyentieuthuyet, R.string.title_khoahoc,
            R.string.title_vanhocnghethuat, R.string.title_tamlinh, R.string.title_doisong
    };

    private CardView[] cardViews = new CardView[9];
    private ImageView[] imageViews = new ImageView[9];
    private Toolbar theloaiToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

        bindViews();

        // Set click listeners for cardViews
        for (int i = 0; i < CARD_VIEW_IDS.length; i++) {
            cardViews[i].setOnClickListener(this);
        }

        // Set toolbar
        setSupportActionBar(theloaiToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        theloaiToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index = Arrays.asList(cardViews).indexOf(v);
        if (index != -1) {
            Intent intent = new Intent(getApplicationContext(), TheLoai_Item.class);
            intent.putParcelableArrayListExtra("array", getArrayList(index));
            intent.putExtra("img", DRAWABLE_IDS[index]);
            intent.putExtra("title", getString(TITLE_IDS[index]));
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    private ArrayList<SachFB> getArrayList(int index) {
        switch (index) {
            case 0:
                return fragment_scrollview.ms1;
            case 1:
                return fragment_scrollview.ms2;
            case 2:
                return fragment_scrollview.ms3;
            case 3:
                return fragment_scrollview.ms4;
            case 4:
                return fragment_scrollview.ms5;
            case 5:
                return fragment_scrollview.ms6;
            case 6:
                return fragment_scrollview.ms7;
            case 7:
                return fragment_scrollview.ms8;
            case 8:
                return fragment_scrollview.ms9;
            default:
                return new ArrayList<>(); // Handle default case accordingly
        }
    }

    private void bindViews() {
        theloaiToolbar = findViewById(R.id.toolbar);
        for (int i = 0; i < CARD_VIEW_IDS.length; i++) {
            cardViews[i] = findViewById(CARD_VIEW_IDS[i]);
            imageViews[i] = findViewById(IMAGE_VIEW_IDS[i]);
        }
    }
}
