package com.app.smartlibhost.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.andremion.counterfab.CounterFab;
import com.app.smartlibhost.epubparser.EpubParseActivity;
import com.app.smartlibhost.model.SachFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.PowerManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.smartlibhost.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.RotationRatingBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter;

public class BookDetailsActivity extends AppCompatActivity {
    ImageView img_sach,img_toolbar;
    TextView tensach,tacgia,nxb,slcl,mota,theloai,ngonngu,barcode;
    Toolbar detail_toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    WebView mWebViewComments;
    NestedScrollView nestedScrollView;
    private WebView mWebviewPop;
    private String postUrl;
    boolean isLoading;
    private static final int NUMBER_OF_COMMENTS = 5;
    private ProgressBar progressBar;
    private FrameLayout mContainer;
    private static String TAG = BookDetailsActivity.class.getSimpleName();
    String id_sach ;
    Button readnow,borrownow;
    android.app.ProgressDialog mProgressDialog;
    SachFB sach;
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdata = mdatabase.getReference();
    CounterFab fab;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://smartlib-c11dc.appspot.com/Ebooks");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_details_);
        Anhxa();
        ActionToolbar();
        GetInformation();
        postUrl = "http://smartlib.keyshop.vn/"+id_sach;
        setLoading(true);
        loadComments();
        loadanim();
        loadRatingBar();
        FiveStarsRating();
        setupFAB();
    }
    private void setupFAB() {
        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookDetailsActivity.this,CartActivity.class));
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


    private void FiveStarsRating() {
        final RotationRatingBar rotationRatingBar = (RotationRatingBar) findViewById(R.id.rotationratingbar);
        rotationRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                Log.d(TAG, "RotationRatingBar onRatingChange: " + rating);
            }
        });
    }

    private void loadRatingBar() {
        RatingReviews ratingReviews = (RatingReviews) findViewById(R.id.rating_reviews);

        Pair colors[] = new Pair[]{
                new Pair<>(Color.parseColor("#0c96c7"), Color.parseColor("#00fe77")),
                new Pair<>(Color.parseColor("#7b0ab4"), Color.parseColor("#ff069c")),
                new Pair<>(Color.parseColor("#fe6522"), Color.parseColor("#fdd116")),
                new Pair<>(Color.parseColor("#104bff"), Color.parseColor("#67cef6")),
                new Pair<>(Color.parseColor("#ff5d9b"), Color.parseColor("#ffaa69"))
        };

        int minValue = 30;

        int raters[] = new int[]{
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1)
        };


        ratingReviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);
    }


    private void loadanim() {
        mWebViewComments.clearCache(true);
        mWebViewComments.reload();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setLoading(false);

            }
        },2500);
    }

    private void loadComments() {
        WebSettings webSettings = mWebViewComments.getSettings();
        mWebViewComments.setWebViewClient(new UriWebViewClient());
        mWebViewComments.setWebChromeClient(new UriChromeClient());
        mWebViewComments.getSettings().setJavaScriptEnabled(true);
        mWebViewComments.getSettings().setDomStorageEnabled(true);
        mWebViewComments.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewComments.getSettings().setSupportMultipleWindows(true);
        mWebViewComments.getSettings().setSupportZoom(false);
        mWebViewComments.getSettings().setBuiltInZoomControls(false);
        mWebViewComments.getSettings().setSavePassword(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebViewComments.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebViewComments, true);
        }

        // facebook comment widget including the article url
        String html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>";

        mWebViewComments.loadDataWithBaseURL("http://smartlib.keyshop.vn/", html, "text/html", "UTF-8", null);
        mWebViewComments.setMinimumHeight(200);
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;

        if (isLoading == true){
            progressBar.setVisibility(View.VISIBLE);
    }
        else
           if (isLoading == false)
            progressBar.setVisibility(View.GONE);

        invalidateOptionsMenu();
    }

    private class UriWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            String host = Uri.parse(url).getHost();

            return !host.equals("m.facebook.com");

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String host = Uri.parse(url).getHost();

             if (url.contains("/plugins/close_popup.php?reload")) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mContainer.removeView(mWebviewPop);
                        loadComments();
                    }
                }, 600);
            }


        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            setLoading(false);
        }
    }
    public class NestedScrollViewOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {
        protected final NestedScrollView mView;

        public NestedScrollViewOverScrollDecorAdapter(NestedScrollView view) {
            this.mView = view;
        }

        public View getView() {
            return this.mView;
        }

        public boolean isInAbsoluteStart() {


            return !this.mView.canScrollVertically(-1);
        }

        public boolean isInAbsoluteEnd() {
            return !this.mView.canScrollVertically(1);
        }
    }


    class UriChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            mWebviewPop = new WebView(getApplicationContext());
            mWebviewPop.setVerticalScrollBarEnabled(false);
            mWebviewPop.setHorizontalScrollBarEnabled(false);
            mWebviewPop.setWebViewClient(new UriWebViewClient());
            mWebviewPop.setWebChromeClient(this);
            mWebviewPop.getSettings().setJavaScriptEnabled(true);
            mWebviewPop.getSettings().setDomStorageEnabled(true);
            mWebviewPop.getSettings().setSupportZoom(false);
            mWebviewPop.getSettings().setBuiltInZoomControls(false);
            mWebviewPop.getSettings().setSupportMultipleWindows(true);

            mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));


            mContainer.addView(mWebviewPop);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mWebviewPop);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.i(TAG, "onConsoleMessage: " + cm.message());
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }
    }

    private void GetInformation() {
        Intent intent = getIntent();
          sach = (SachFB) intent.getSerializableExtra("thongtinsach");
       tensach.setText(sach.getTensach());
       id_sach = sach.getId_sach();
       tacgia.setText("Tác giả: "+sach.getTentacgia());
       String id_theloai = sach.getId_theloai();
       String tloai = "";
       switch (id_theloai){

           case "1":
               tloai = "Sách thiếu nhi";
             break;
           case "2":
                tloai ="Giáo trình";
               break;
           case "3":
               tloai ="Chính trị - Pháp luật";

               break;
           case "4":
                tloai ="Văn hóa xã hội - Lịch sử";
               break;

           case "5":
                tloai ="Truyện - Tiểu thuyết";
               break;

           case "6":
                tloai ="Khoa học - Kinh tế";
               break;

           case "7":
                tloai ="Văn học nghệ thuật";
               break;

           case "8":
                tloai ="Tâm linh, Tôn giáo";
               break;

           case "9":
                tloai ="Đời sống";
               break;
        default:


       }
       theloai.setText("Thể loại:    "+ tloai);
       String id_nn = sach.getId_ngonngu();
       String nn = "";
       switch (id_nn){
           case "1":
               nn ="Tiếng Việt";
               break;
           case "2":
               nn ="Tiếng Anh";
               break;
       }
       ngonngu.setText("Ngôn ngữ:    "+nn);
       nxb.setText("NXB:    "+sach.getNXB());
       barcode.setText("Mã kiểm soát:    "+sach.getBarcode());
       slcl.setText("SL: "+sach.getSoluong()+"      Còn lại: "+sach.getConlai());
       mota.setText(sach.getMota());
        Picasso.get().load(sach.getImg_sach())
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).
                into(img_sach);
        Picasso.get().load(sach.getImg_sach())
                .into(img_toolbar);
        img_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookDetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.photoview);
                Picasso.get().load(sach.getImg_sach()).
                        into(photoView);

                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();


                mDialog.show();
               // mDialog.getWindow().setLayout(1250, 1800);


            }
        });

    }

    private void ActionToolbar() {
        setSupportActionBar(detail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detail_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setTitle("Details");

        /*collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);*/
    }


    private void Anhxa() {

        readnow = (Button) findViewById(R.id.readnow);
        readnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                GetEbookUrl();
                setupProgresdialog();

            }
            private void GetEbookUrl() {
            }
            private void setupProgresdialog() {
                String filename = sach.getBarcode()+".epub";
                File file = new File(Environment.getExternalStorageDirectory().getPath(),filename);
                Log.d("Ebook",file.toString());
                if(file.exists()){

                    startActivity(new Intent(getApplicationContext(), EpubParseActivity.class).putExtra("paths","/sdcard/"+filename));

                }
                else{

                            storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                               if (uri == null){
                                   Toast.makeText(getApplicationContext(),"Sách hiện chưa có Ebook, vui lòng quay lại sau !",Toast.LENGTH_LONG).show();

                               } else
                               {
                                   Log.d("Ebooks",String.valueOf(uri));
                                   // instantiate it within the onCreate method
                                   mProgressDialog = new android.app.ProgressDialog(BookDetailsActivity.this);
                                   mProgressDialog.setMessage("Downloading...");
                                   mProgressDialog.setIndeterminate(true);
                                   mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                   mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
                                   final DownloadTask downloadTask = new DownloadTask( BookDetailsActivity.this);
                                   downloadTask.execute(uri.toString());

                                   mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                       @Override
                                       public void onCancel(DialogInterface dialog) {
                                           downloadTask.cancel(true);
                                       }
                                   });

                               }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Sách hiện chưa có Ebook, vui lòng quay lại sau !",Toast.LENGTH_LONG).show();

                                }
                            });


                }

            }
        });

        borrownow = (Button) findViewById(R.id.borrownow);
        borrownow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Đã thêm vào túi",Toast.LENGTH_SHORT).show();
              String  UId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mdata.child("Users").child(UId).child("Cart").child(sach.getBarcode()).setValue(sach);



            }
        });

        theloai = (TextView) findViewById(R.id.tvtheloai);
        ngonngu = (TextView) findViewById(R.id.tvnn);
        barcode = (TextView) findViewById(R.id.barcode);
        img_toolbar = (ImageView) findViewById(R.id.imagetoolbar);
       detail_toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        img_sach = (ImageView) findViewById(R.id.image_sach);
        tensach = (TextView) findViewById(R.id.tensach);
        tacgia = (TextView) findViewById(R.id.tacgia);
        nxb = (TextView) findViewById(R.id.nxb);
        slcl = (TextView) findViewById(R.id.slcl);
        mota = (TextView) findViewById(R.id.mota);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        OverScrollDecoratorHelper.setUpStaticOverScroll(coordinatorLayout, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        mWebViewComments = (WebView) findViewById(R.id.commentsView);
        mContainer = (FrameLayout) findViewById(R.id.webview_frame);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) findViewById(R.id.mainlayout);
       // new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(nestedScrollView));

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
             //   File destination = new File(context.getFilesDir(), "/EpubFolder/test.epub");
              //  destination.createNewFile();

                File destination = new File(Environment.getExternalStorageDirectory().getPath(),sach.getBarcode()+".epub");
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(destination);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (s != null)
                Toast.makeText(context,"Download error: "+s, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }
    }}