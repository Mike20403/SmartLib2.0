package com.app.smartlibhost.activity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.andremion.counterfab.CounterFab;
import com.app.smartlibhost.Fragment.PopupDialogFragment;
import com.app.smartlibhost.Fragment.fragment_scrollview;
import com.app.smartlibhost.Fragment.fragment_tracuu;
import com.app.smartlibhost.R;
import com.app.smartlibhost.activity.TheloaiActivity.TheLoai;
import com.app.smartlibhost.adapter.Menuadapter;
import com.app.smartlibhost.adapter.Users_adapter;
import com.app.smartlibhost.model.Menu_main;
import com.app.smartlibhost.model.Users;
import com.app.smartlibhost.ultil.CheckConnection;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.AbsListViewOverScrollDecorAdapter;
import me.everything.android.ui.overscroll.adapters.ViewPagerOverScrollDecorAdapter;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import nl.siegmann.epublib.epub.Main;

import static com.app.smartlibhost.activity.BorrowActivity.users_adapter;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static String ten_menu ="test";
    public static String img_menu ="";
    public static int id_menu =0;
    private final int[] COLORS = {0xFF2196F3, 0xFF00BCD4, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
    public static String email,name;
    NavigationController mNavigationController;
    ListView lvmain;
    NavigationView navigationView;
    Toolbar toolbarmain;
    DrawerLayout drawerLayout;
    PageNavigationView tabLayout;
    ViewPager viewPager;
    ArrayList<Menu_main> mang_menu;
    Menuadapter menuadapter;
    ImageView avatar;
    TextView emaill, namee;
    public static ArrayList<Users> manguser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GoogleSignInClient gsc;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String UId;
    String PhotoURL = FirebaseStorage.getInstance().getReference("UserImg/default.png").getPath();
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdata = mdatabase.getReference();
    CounterFab fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            GetMenu();
            SetUpViewPager();
        } else {
            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
        ListViewOnClick();
        SetInfo();
        RequestPermissions();
    }

    private void RequestPermissions() {
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1
            );
        }
    }


    private void AddUserToFirebase() {
       UId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final Map<String, Object> userr = new HashMap<>();
        FirebaseUser user = mAuth.getCurrentUser();
        userr.put("Name", user.getDisplayName());
        userr.put("Email", user.getEmail());
        userr.put("PhoneNumber", user.getPhoneNumber());
        userr.put("PhotoURL", PhotoURL);

        mdata.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UId )){
                    if (dataSnapshot.child(UId).hasChild("Phone")){ } else
                    {
                        new PopupDialogFragment().show(getSupportFragmentManager(), PopupDialogFragment.class.getSimpleName());
                    }


                } else {
                    mdata.child("Users").child(UId).setValue(userr);
                    new PopupDialogFragment().show(getSupportFragmentManager(), PopupDialogFragment.class.getSimpleName());
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdata.child("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.child("luotclick").getRef().removeValue();
                    ds.child("id_sach").getRef().setValue(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ListViewOnClick() {
        lvmain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Menu_main menu = mang_menu.get(position);
                Log.d("DEBUG",menu.toString());


                if (menu.getId() == 6) {
                    showAlertDialog();
                }
                if (menu.getId() == 4) {
                    startActivity(new Intent(MainActivity.this, AddingBookActiviy.class));
                }
                if (menu.getId() == 2) {
                    startActivity(new Intent(MainActivity.this, BorrowActivity.class));
                }
                if (menu.getId() == 5) {
                    startActivity(new Intent(MainActivity.this, LineGraphActivity.class));
                }
                if (menu.getId() == 3){
                    startActivity(new Intent(MainActivity.this,MemberCardActivity.class));

                }
                if (menu.getId() == 1) {
                    startActivity(new Intent(MainActivity.this, TheLoai.class));
                }

            }
        });
    }

    private void SetUpViewPager() {
        setupViewPager(viewPager);
        mNavigationController = tabLayout.material()
                .addItem(R.drawable.house, "Trang chủ", COLORS[0])
                .addItem(R.drawable.search, "Tra cứu", COLORS[1])
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
                fragment_scrollview.scrollView.post(new Runnable() {
                    @Override
                    public void run() {

                        fragment_scrollview.scrollView.fullScroll(ScrollView.FOCUS_UP);

                    }
                });
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getApplicationWindowToken(), 0);
                fragment_tracuu.editText.setText("");

            }
        });


        //FLOATING ACTION BUTTON

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });
        mdata.child("Users").child((FirebaseAuth.getInstance().getUid())).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                fab.setCount(size);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            showAlertDialog();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public static class NukeSSLCerts {
        protected static final String TAG = "NukeSSLCerts";

        public static void nuke() {
            try {
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Smartlib");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               finish();
                dialogInterface.dismiss();
                mAuth.signOut();
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void GetFBData() {
       Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        name = intent.getStringExtra("Name");
        // id_fb_user = intent.getStringExtra("myid");
        Log.d("BBB",email+" "+name);
    }

    private void SetInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("Test",user.getUid());
        if (user.getDisplayName() != null) {
            namee.setText(user.getDisplayName());
        }
        emaill.setText(user.getEmail());
        PhotoURL = String.valueOf(user.getPhotoUrl());
        String photoUrl = PhotoURL;
        photoUrl = photoUrl + "?height=500";
        Picasso.get().load(photoUrl)
                .placeholder(R.drawable.boy)
                .error(R.drawable.boy)
                .into(avatar);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_scrollview(),null);
        adapter.addFragment(new fragment_tracuu(),null);
       // adapter.addFragment(new ThreeFragment(), "Three");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        new HorizontalOverScrollBounceEffectDecorator(new ViewPagerOverScrollDecorAdapter(viewPager));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
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

    public static class  Khaibao {
        public static  String id_sach;
        public  static String barcode;
        public  static String id_theloai;
        public static String id_ngonngu;
        public static String tensach;
        public static String tentacgia;
        public static String Mota;
        public static String NXB;
        public static String soluong;
        public static String img_sach;
        public static String conlai;

    }

    private void GetMenu() {
        mang_menu.clear();
        mdata.child("Menu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Get the values from the DataSnapshot
                int id = snapshot.child("id").getValue(Integer.class);
                String ten_menu = snapshot.child("name").getValue(String.class);
                String img_menu = snapshot.child("img").getValue(String.class);

                // Create a Menu_main object and add it to the mang_menu list
                Menu_main menu = new Menu_main(id, ten_menu, img_menu);
                mang_menu.add(menu);

                // Notify the adapter that the data set has changed
                menuadapter.notifyDataSetChanged();
                Log.d("DEBUG",snapshot.toString());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private  void ActionBar() {
        setSupportActionBar(toolbarmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarmain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarmain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);

        ImageView locButton = (ImageView) MenuItemCompat.getActionView(menu.findItem(R.id.menuRefresh));
        final Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise_refresh);
        if (locButton != null) {
            locButton.setImageResource(R.drawable.reload24px);
            // need some resize

            locButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(rotation);
                    // create and use new data set
                    GetMenu();
                    SetInfo();
                    viewPager.removeAllViews();
                    setupViewPager(viewPager);
                    mNavigationController.setupWithViewPager(viewPager);


                }
            });
        }
        return true;
    }

    public void Anhxa() {
        fab = (CounterFab) findViewById(R.id.fab);
        navigationView = (NavigationView) findViewById(R.id.navigation_main);
        toolbarmain = findViewById(R.id.toolbarmain);
        lvmain = (ListView) findViewById(R.id.lvmain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        emaill = findViewById(R.id.activity_main_tv_email);
        namee = findViewById(R.id.name_user);
        avatar = findViewById(R.id.avatar_user);
        mang_menu = new ArrayList<>();
        menuadapter = new Menuadapter(mang_menu,R.layout.dong_lvmenu,getApplicationContext());
        menuadapter.notifyDataSetChanged();
        lvmain.setAdapter(menuadapter);
        tabLayout = (PageNavigationView) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        manguser = new ArrayList<>();
        users_adapter = new Users_adapter(this,R.layout.dong_user,manguser);
        new VerticalOverScrollBounceEffectDecorator(new AbsListViewOverScrollDecorAdapter(lvmain));
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {

            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
    }
}


