package com.app.smartlibhost.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.app.smartlibhost.R;
import com.app.smartlibhost.activity.AddingBookActiviy;
import com.app.smartlibhost.adapter.DialogAdapter;
import com.app.smartlibhost.adapter.DialogAdapterrr;
import com.app.smartlibhost.epubparser.ViewPagerAdapter;
import com.app.smartlibhost.model.SachFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.ViewPagerOverScrollDecorAdapter;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

import static com.app.smartlibhost.Fragment.fragment_addinfor1.Checkedt;
import static com.app.smartlibhost.Fragment.fragment_addinfor1.GetAddress;
import static com.app.smartlibhost.Fragment.fragment_addinfor1.Getjob;
import static com.app.smartlibhost.Fragment.fragment_addinfor1.Getsdt;
import static com.app.smartlibhost.Fragment.fragment_addinfor2.GetResultState;
import static com.app.smartlibhost.Fragment.fragment_addinfor2.GetFaceReg;
import static com.app.smartlibhost.Fragment.fragment_addinfor2.GetCMND;

public class PopupDialogFragment extends DialogFragment {
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mdata = database.getReference();
    String Uid = FirebaseAuth.getInstance().getUid();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://smartlib-c11dc.appspot.com/UserInfo");
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    String DowloadURL;
    private final int[] COLORS = {0xFF2196F3, 0xFF00BCD4, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adddialog, container);

        TabLayout dialogTabLayout = (TabLayout) view.findViewById(R.id.tabLayoutt);
        ViewPager dialogViewPager = (ViewPager) view.findViewById(R.id.addPager);
        button = (Button) view.findViewById(R.id.dismiss);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GetResultState() != null) {
                    if (Checkedt() == true && GetResultState().equals("Valid") ){
                        mdata.child("Users").child(Uid).child("Phone").setValue(Getsdt());
                        mdata.child("Users").child(Uid).child("Address").setValue(GetAddress());
                        mdata.child("Users").child(Uid).child("Job").setValue(Getjob());
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        UploadFace(GetFaceReg());
                        UploadCMND(GetCMND());
                        getDialog().dismiss();

                    }else
                    {

                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ, chính xác thông tin và hoàn tất quét CMND", Toast.LENGTH_SHORT).show();
                }

            }

            private void UploadCMND(Bitmap bitmap) {
                String fileimg = Uid+"cmnd.png";
                final StorageReference mstorage = storageReference.child(fileimg);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] Data = baos.toByteArray();
                final UploadTask uploadTask = mstorage.putBytes(Data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child(fileimg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("check", String.valueOf(uri));
                                DowloadURL = String.valueOf(uri);
                                Log.d("check", DowloadURL);





                            }
                        });

                    }
                });
            }

            private void UploadFace(Bitmap bitmap) {
               String fileimg = Uid+"face.png";
                final StorageReference mstorage = storageReference.child(fileimg);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] Data = baos.toByteArray();
                final UploadTask uploadTask = mstorage.putBytes(Data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child(fileimg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("check", String.valueOf(uri));
                                DowloadURL = String.valueOf(uri);
                                Log.d("check", DowloadURL);





                            }
                        });

                    }
                });
            }
        });

        dialogTabLayout.addTab(dialogTabLayout.newTab().setText("Add Info"));
        dialogTabLayout.addTab(dialogTabLayout.newTab().setText("Scan ID"));
        FragmentManager manager = getChildFragmentManager();
        dialogViewPager.setAdapter(new DialogAdapterrr(manager));
        dialogTabLayout.setupWithViewPager(dialogViewPager);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);

        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width,1800);
        }
    }
}
