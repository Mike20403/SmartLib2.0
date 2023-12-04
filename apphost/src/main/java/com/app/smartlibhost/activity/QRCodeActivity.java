package com.app.smartlibhost.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.app.smartlibhost.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;

public class QRCodeActivity extends AppCompatActivity {

    String qrdata;
    androidx.appcompat.widget.Toolbar tb;
    String key;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://smartlib-c11dc.appspot.com/QRCode");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getReference().child("Admin").child("Queue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
       // Anhxa();
       // ActionToolbar();
       // CallQRCodeAPI();
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

    private void Anhxa() {
        tb = (Toolbar) findViewById(R.id.toolbar);
       // qrcode = (ImageView) findViewById(R.id.qr_img);
        Intent intent = getIntent();
        qrdata= intent.getStringExtra("QRCode");
        key = intent.getStringExtra("key");
        mref.child(key).child("key").setValue(key);



    }

    private void CallQRCodeAPI() {
        /*Picasso.with(getApplicationContext()).load("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+qrdata)
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                .into(qrcode);
*/
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrdata, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

              //  qrcode.setImageBitmap(bmp);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String filename = key+".png";
            StorageReference mstorage = storageReference.child(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] Data = baos.toByteArray();
            final UploadTask uploadTask = mstorage.putBytes(Data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String DowloadURL = String.valueOf(uri);
                            mref.child(key).child("QRCode").setValue(DowloadURL);


                        }
                    });


                }
            });


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}
