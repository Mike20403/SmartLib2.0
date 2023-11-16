package com.app.smartlibhost.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.andremion.counterfab.CounterFab;
import com.app.smartlibhost.R;
import com.app.smartlibhost.model.Order;
import com.app.smartlibhost.model.SachFB;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CartActivity extends AppCompatActivity  {
 RecyclerView cartrv;
    FirebaseRecyclerOptions<SachFB> options;
    FirebaseRecyclerAdapter<SachFB,BookViewHolder> adapter;
    ArrayList<SachFB> arraysach = new ArrayList<>();
    String  UId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mdata = database.getReference().child("Users").child(UId).child("Cart");
    DatabaseReference admdata = database.getReference().child("Admin").child("Queue");
    CounterFab fab;
    Toolbar tb;
    ImageView qrcode;
    Button borrownow;
    ImageView emptyimg;
    TextView emptytxt;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        ActionToolbar();

            SetupRV();

        //startActivity(new Intent(getApplicationContext(), EpubParseActivity.class).putExtra("paths","/storage/sdcard/Download/de_men_phieu_luu_ky__to_hoai.epub"));


        }

    private void movechild(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SachFB> arrayList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("Dataa",String.valueOf(ds));
                    SachFB sach = ds.getValue(SachFB.class);
                    arrayList.add(sach);
                }
                Map map = new HashMap();
                Map mapp = new HashMap();

                map.put("timestamp", ServerValue.TIMESTAMP);
                mapp.put("timestamp",ServerValue.TIMESTAMP);
                Order morder = new Order(arrayList, Calendar.getInstance().getTimeInMillis(),UId,"Đã khởi tạo",null,key);

                toPath.child(key).setValue(morder, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });



                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void GetData() {
        arraysach.clear();
        Log.d("Uid",UId);
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    SachFB sachFB = ds.getValue(SachFB.class);
                    Log.d("Dataa",sachFB.toString());
                    arraysach.add(sachFB);

                }
                if (arraysach.size() == 0){
                    emptyimg.setVisibility(View.VISIBLE);
                    emptytxt.setVisibility(View.VISIBLE);
                } else {
                    emptytxt.setVisibility(View.INVISIBLE);
                    emptyimg.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        GetData();



    }

    private void Anhxa() {
        emptyimg = (ImageView) findViewById(R.id.emptyimg);
        emptytxt = (TextView) findViewById(R.id.emptytext);

        borrownow = (Button) findViewById(R.id.borrownow);
        tb = (Toolbar) findViewById(R.id.toolbar);
        borrownow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetData();
                showAlertDialog();
            }
        });



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

    private void SetupRV () {
        cartrv = (RecyclerView) findViewById(R.id.cartrv);
        options = new FirebaseRecyclerOptions.Builder<SachFB>().setQuery(mdata, SachFB.class).build();
        adapter = new FirebaseRecyclerAdapter<SachFB, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull SachFB model) {

                holder.tensach.setText(model.getTensach());
                Picasso.get().load(model.getImg_sach())
                        .placeholder(R.drawable.no_img)
                        .error(R.drawable.no_img)
                        .into(holder.img_sach);
                holder.tacgia.setText("Tác giả: " + model.getTentacgia());
               // holder.slconlai.setText("SL: :" + model.getSoluong() + " Còn lại: " + model.getConlai());

            }

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_cart, parent, false);
                return new BookViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        cartrv.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        cartrv.setAdapter(adapter);
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Smartlib");
        builder.setMessage("Bạn có chắc muốn mượn không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Chắc chắn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (arraysach.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Túi của bạn đang trống, vui lòng chọn thêm sách !", Toast.LENGTH_SHORT).show();

                } else {
                    String qrdata ="";
                    for (int j = 0 ; j<arraysach.size(); j++){
                        if (j!= arraysach.size()-1){
                            qrdata = qrdata +arraysach.get(j).getBarcode()+"\n";
                        }else {  qrdata += arraysach.get(j).getBarcode();}


                    }
                    Log.d("Tempp",qrdata);



                    key = admdata.push().getKey();
                    movechild(mdata,admdata);
                    showQRDialog(key);


                    dialogInterface.dismiss();

                }






            }

            private void showQRDialog(String key) {
                Dialog dialog = new Dialog(CartActivity.this);
                dialog.setContentView(R.layout.qrdialog);

               qrcode = (ImageView) dialog.findViewById(R.id.qr_img);
                CallQRCode(key);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        GetData();
                    }
                });

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void CallQRCode(String data) {
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


    @Override
    protected void onStart() {
        if (adapter != null) {
            adapter.startListening();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (adapter != null ){
            adapter.stopListening();
        }
        super.onStop();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_sach;
        public TextView tensach, tacgia, slconlai;
        public ImageButton delete;

        public BookViewHolder(final View itemView) {
            super(itemView);

            img_sach = (ImageView) itemView.findViewById(R.id.imgsach);
            tensach = (TextView) itemView.findViewById(R.id.tensach);
            tacgia = (TextView) itemView.findViewById(R.id.tacgia);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_from_bottom);
            itemView.startAnimation(animation);

        }
    }

    }

