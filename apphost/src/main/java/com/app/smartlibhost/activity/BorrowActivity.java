package com.app.smartlibhost.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.app.smartlibhost.Fragment.fragment_history;
import com.app.smartlibhost.Fragment.fragment_order;
import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.DialogAdapter;
import com.app.smartlibhost.adapter.Users_adapter;
import com.app.smartlibhost.model.Order;
import com.app.smartlibhost.other.NoTouchViewPager;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.ViewPagerOverScrollDecorAdapter;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import static java.text.DateFormat.getDateTimeInstance;


public class BorrowActivity extends AppCompatActivity {
    Toolbar tb;
    RecyclerView rv,borrowlogs;
    PageNavigationView tabLayout;
    ImageView Qrcode,qrimg;
    NavigationController mNavigationController;
    public static BorrowActivity context;
    public static ListView lv_user;
    public static Users_adapter users_adapter;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    DatabaseReference mdata = FirebaseDatabase.getInstance().getReference().child("Books");
    Button Verify;
    NoTouchViewPager viewPager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("Admin").child("Queue");


    private final int[] COLORS = {0xFFFC0000, 0xFFF57F17, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        Anhxa();
        ActionToolbar();
        SetUpViewPager();
    }


    private void Anhxa() {
        tabLayout = (PageNavigationView) findViewById(R.id.tabs);
        viewPager= (NoTouchViewPager) findViewById(R.id.viewpager);
        tb = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new BorrowActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_order(),null);
        adapter.addFragment(new fragment_history(),null);
        viewPager.setAdapter(adapter);
        new HorizontalOverScrollBounceEffectDecorator(new ViewPagerOverScrollDecorAdapter(viewPager));
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
    private void SetUpViewPager() {
        setupViewPager(viewPager);
        mNavigationController = tabLayout.material()
                .addItem(R.drawable.timeline, "Your current borrowing", COLORS[0])
                .addItem(R.drawable.historyorder, "History", COLORS[1])
                .enableAnimateLayoutChanges()
                .build();
        mNavigationController.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });

        //FLOATING ACTION BUTTON




    }
    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }


    @Override
    protected void onStart() {

        super.onStart();
    }
    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting2, menu);

        ImageView qrscan = (ImageView) MenuItemCompat.getActionView(menu.findItem(R.id.menuRefresh));
        final Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise_refresh);
        if (qrscan != null) {
            qrscan.setImageResource(R.drawable.qrscan);
            // need some resize

            qrscan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(rotation);
                    // create and use new data set

                   FragmentManager supportFragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                    Fragment fragmentById = supportFragmentManager.findFragmentById(com.notbytes.barcode_reader.R.id.fm_container);
                    if (fragmentById != null) {
                        fragmentTransaction.remove(fragmentById);
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                    launchBarCodeActivity();

                }
            });
        }
        return true;
    }
    public void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(getApplicationContext(), true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
                      case BARCODE_READER_ACTIVITY_REQUEST:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "error in  scanning", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
                    Toast.makeText(getApplicationContext(), barcode.rawValue, Toast.LENGTH_SHORT).show();

                    findorder(barcode.rawValue);

                }
                break;
        }




    }

    private void findorder(String rawValue) {
        ref.child(rawValue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Order order =  dataSnapshot.getValue(Order.class);
                showdialog(order,BorrowActivity.this);
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void CallQRCode(String data, ImageView qrcode) {
        /*Picasso.with(getApplicationContext()).load("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+qrdata)
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                .into(qrcode);

*/
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            qrcode.setImageBitmap(bmp);



        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    private void showdialog(Order order, Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogorder2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        Verify = (Button) dialog.findViewById(R.id.verify);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(order.getKey()).child("status").setValue("Đã xác nhận");
                dialog.dismiss();
                Toast.makeText(context,"Trạng thái đơn mượn thay đổi: Đã xác nhận",Toast.LENGTH_SHORT).show();
            }
        });

        borrowlogs = (RecyclerView) dialog.findViewById(R.id.borrowlogs);
        borrowlogs.setHasFixedSize(true);
        borrowlogs.setLayoutManager(new LinearLayoutManager(context));
        SimpleDateFormat formatter = new SimpleDateFormat();
        String borrowstr = formatter.format(order.getDate_borrowed());
        DialogAdapter adapter = new DialogAdapter(context,order.getBorrowbooks(),borrowstr,"20/11/2019");
        borrowlogs.setAdapter(adapter);
        dialog.show();


    }

    /*public void Anhxa() {
        tb = (Toolbar) findViewById(R.id.borrowmanagement);
        lv_user = (ListView) findViewById(R.id.lv_users);
        lv_user.setAdapter(users_adapter);
        users_adapter.notifyDataSetChanged();
        usersearch = (EditText) findViewById(R.id.thanhtimkiem);
        searchbutton = (ImageButton) findViewById(R.id.searchbutton);
        scanbt = (Button) findViewById(R.id.scanbt);


    }*/
   /* public static void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }*/
   public void ActionToolbar() {
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
