package com.app.smartlibhost.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.app.smartlibhost.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MemberCardActivity extends AppCompatActivity  {
    Toolbar tb;
    ImageView cmnd,facereg;
    FirebaseStorage mstorage = FirebaseStorage.getInstance();
    StorageReference storageReference = mstorage.getReference();
    String Uid = FirebaseAuth.getInstance().getUid();

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_card);
        Anhxa();
        ActionToolbar();
        SetData();
    }

    private void SetData() {
        storageReference.child("UserInfo").child(Uid+"cmnd.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                Picasso.get().load( task.getResult().toString())
                        .placeholder(R.drawable.no_img)
                        .error(R.drawable.no_img)
                        .into(cmnd);
            }
        });
        storageReference.child("UserInfo").child(Uid+"face.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                Picasso.get().load( task.getResult().toString())
                        .placeholder(R.drawable.no_img)
                        .error(R.drawable.no_img)
                        .into(facereg);
            }
        });

    }




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
    private void Anhxa() {
        tb = (Toolbar) findViewById(R.id.toolbar);
        cmnd =(ImageView) findViewById(R.id.CMND);
        facereg =(ImageView)  findViewById(R.id.faceregconize);
        facereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberCardActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.photoview);
                Picasso.with(getApplicationContext()).load(sach.getImg_sach()).
                        into(photoView);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
                // mDialog.getWindow().setLayout(1250, 1800);
*/
            }
        });
    }



}
