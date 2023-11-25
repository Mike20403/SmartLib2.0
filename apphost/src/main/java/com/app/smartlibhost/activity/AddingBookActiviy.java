package com.app.smartlibhost.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.andremion.counterfab.CounterFab;
import com.app.smartlibhost.Fragment.fragment_addsach;
import com.app.smartlibhost.Fragment.fragment_addsl;
import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.lv_adapter;
import com.app.smartlibhost.model.Sach;
import com.app.smartlibhost.other.NoTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.ViewPagerOverScrollDecorAdapter;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;




public class AddingBookActiviy extends AppCompatActivity {
    ListView listView;
    Button addbt;
    Toolbar tb;
    lv_adapter lv_adapter;
    EditText edtten,edttg,edtnn,edtmt,edtnxb,edtsl,edtbarcode;
    ImageButton upload_img;
    Button cancel,confirm;
    Bitmap yourSelectedImage;
    String realpath;
    Sach sach;
     NoTouchViewPager mViewPager;
     PageNavigationView mTab;
     NavigationController mNavigationController;
     CounterFab fab;

    private final int[] COLORS = {0xFF2196F3, 0xFF00BCD4, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
    private final List<Integer> mMessageNumberList = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inp_out_book_);


        Anhxa();
        ActionToolbar();
        initNavigation();



       /* addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialogadd();


            }
        });*/










    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    realpath = getRealPathFromURI(selectedImage);




                     yourSelectedImage = BitmapFactory.decodeFile(realpath);
                    /* Now you have choosen image in Bitmap format in object "yourSelectedImage". You can use it in way you want! */
                   upload_img.setImageBitmap(yourSelectedImage);
                }
        }

    };
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }




 /* public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Smartlib");
        builder.setMessage("Bạn có chắc muốn xóa không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mang_sach.remove(sach);
                lv_adapter.notifyDataSetChanged();
                String imgfolder = sach.getImg_sach();
                imgfolder = imgfolder.substring(imgfolder.lastIndexOf("/"));
                Log.d("BBB",imgfolder);




            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }*/

   /* private void updatemangsach() {
        mang_sach.clear();


            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Sach_url, new com.android.volley.Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response != null) {
                        int i;

                        for (i = 0; i<response.length();i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_sach = jsonObject.getInt("id_sach");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.barcode = jsonObject.getString("barcode");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_ngonngu = jsonObject.getInt("id_ngonngu");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_theloai = jsonObject.getInt("id_theloai");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.tensach = jsonObject.getString("tensach");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.tentacgia = jsonObject.getString("tentacgia");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.NXB = jsonObject.getString("NXB");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.Mota = jsonObject.getString("Mota");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.soluong = jsonObject.getInt("soluong");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.conlai = jsonObject.getInt("conlai");
                                com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.img_sach = jsonObject.getString("img_sach");

                                mang_sach.add(new Sach(com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_sach, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.barcode, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_theloai, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.id_ngonngu,
                                        com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.tensach, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.tentacgia, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.Mota, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.NXB,
                                        com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.soluong, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.img_sach, com.example.com.app.com.app.smartlibhost.activity.MainActivity.Khaibao.conlai));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CheckConnection.ShowToast_short(getApplicationContext(), error.toString());
                    Log.d("AAA", error.toString());
                }
            });
            requestQueue.add(jsonArrayRequest);
        }*/

    private void initNavigation() {
        mNavigationController = mTab.material()
                .addItem(R.drawable.warehousemanagement, "Đầu sách mới", COLORS[0])
                .addItem(R.drawable.themsach, "Thêm sách", COLORS[1])
                .enableAnimateLayoutChanges()
                .build();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new fragment_addsach(),null);
        viewPagerAdapter.addFragment(new fragment_addsl(),null);
        mViewPager.setAdapter(viewPagerAdapter);
        new HorizontalOverScrollBounceEffectDecorator(new ViewPagerOverScrollDecorAdapter(mViewPager));


        mNavigationController.setupWithViewPager(mViewPager);

        // 初始化消息数字为0
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            mMessageNumberList.add(0);
        }

        mMessageNumberList.set(1, Math.max(0, mMessageNumberList.get(1)+1 ));
        mNavigationController.setMessageNumber(1,mMessageNumberList.get(1));
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);

        }

    }


    private void Anhxa() {

        addbt = (Button) findViewById(R.id.btadd);
        tb = (Toolbar) findViewById(R.id.bookmanagement);
        mViewPager = findViewById(R.id.viewPager);
        mTab = findViewById(R.id.tab);




    }
    private void ActionToolbar() {
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

/* Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu est nec augue vestibulum venenatis nec sed augue. Morbi sit amet commodo elit. Vestibulum et purus vel ligula eleifend iaculis. Proin sed dolor non nunc fermentum suscipit at quis tortor. Ut vel semper mauris, eget pharetra leo. Maecenas mattis leo sed fermentum sagittis. Phasellus eu risus in lacus pretium efficitur. Vestibulum volutpat rutrum auctor. Donec venenatis sapien sit amet magna pharetra, in interdum ex lobortis. Aenean volutpat erat a nunc venenatis, id mollis sem ornare. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam ut maximus diam, in porta erat.

Cras at efficitur ligula. Suspendisse lectus dui, lacinia ut luctus in, vulputate id erat. Fusce tristique sit amet nisl non tempus. Etiam facilisis eleifend ligula, id auctor enim pretium quis. Curabitur viverra tortor enim, et iaculis odio rutrum ac. Duis pellentesque leo a leo pellentesque varius. Praesent consequat scelerisque libero quis mattis. Nullam sodales ligula et viverra faucibus. Fusce sollicitudin pellentesque lorem sed pharetra. Curabitur et urna vel neque cursus porttitor quis eu urna. Nunc et nisl viverra, volutpat elit nec, porttitor dolor. Vestibulum nec nisi quis enim cursus ultricies finibus sed dui. Morbi iaculis sed odio consectetur placerat. Vivamus convallis rutrum orci. Pellentesque risus orci, dignissim ac arcu id, ornare rutrum justo. Sed molestie erat vel volutpat rhoncus.

Ut vel velit lacinia, aliquam mi sed, porta lectus. Mauris tincidunt, sem eget faucibus tristique, ligula justo euismod tortor, sit amet semper mauris risus ac nibh. Integer sed dignissim nibh. Fusce tristique purus non sapien viverra placerat. Nulla nec turpis ipsum. Vestibulum tincidunt diam ipsum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Curabitur massa lectus, faucibus ut tincidunt at, luctus vitae sapien. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed eget iaculis eros. Fusce quis felis felis.

Proin nec finibus nulla, porttitor lacinia felis. Nulla venenatis magna vitae placerat consectetur. Suspendisse at ultricies neque. Donec in ante ac nibh volutpat gravida. In in mi urna. Aliquam eu neque vel nibh tincidunt pharetra eu a dui. Mauris cursus, felis eget vestibulum cursus, justo dui auctor arcu, vitae pulvinar elit augue sed quam. Nullam ultrices feugiat libero, at vehicula massa pharetra eget. Suspendisse elementum rutrum dui. Maecenas tincidunt feugiat dui vitae congue. Duis interdum, odio sed pulvinar hendrerit, ante justo ultricies turpis, vel fringilla ex nisi a neque. Nulla sed mi quam. Fusce ac molestie turpis.

Aliquam hendrerit pulvinar imperdiet. Praesent finibus, lacus ultricies suscipit cursus, ante lectus sollicitudin est, dapibus pharetra mauris mi sit amet diam. Nulla facilisi. Pellentesque fermentum a nulla id eleifend. Suspendisse quam dui, venenatis ac consectetur a, venenatis nec erat. Fusce ut dolor quis justo viverra accumsan sit amet ac dui. Nulla ullamcorper urna nec elit feugiat elementum ut at orci. */ 