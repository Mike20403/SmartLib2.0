package com.app.smartlibhost.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.SachFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.notbytes.barcode_reader.BarcodeReaderActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

public class fragment_addsach extends Fragment {
    View view;

    EditText edtbarcode,edtten,edttg,edtmt,edtnxb,edtsl,cancel,confirm;
    String message,realpath,tensach,tentacgia,id_ngonngu,id_theloai,Mota,NXB,soluong,conlai,img_sach,barcode;
    Button bt;
    ScrollView scrollView;
    Spinner spinnernn,spinnertheloai;
    ImageView scanbt,upload_img;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    Bitmap yourSelectedImage;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://smartlib-c11dc.appspot.com/IMGBooks");
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdata = mdatabase.getReference();
    int id ;
    String DowloadURL;
    String fileimg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addsach,container,false);
        Anhxa();
        initbarcode();
       HideEdtKBoard();

        return view;
    }

    private void HideEdtKBoard() {
        edtten.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtten.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtten.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edttg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edttg.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edttg.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edtbarcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        edtsl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtsl.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtsl.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edtmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtmt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtmt.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edtnxb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtnxb.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtnxb.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void Anhxa() {
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        bt = (Button) view.findViewById(R.id.addnow);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.child("Books").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            id = Integer.parseInt(childSnapshot.getKey())+1;






                            GetEdtText();
                            fileimg = "IMG"+Calendar.getInstance().getTimeInMillis()+".png";
                            final StorageReference mstorage = storageReference.child(fileimg);
                            Log.d("Ref",mstorage.toString());


                            upload_img.setDrawingCacheEnabled(true);
                            upload_img.buildDrawingCache();
                            Bitmap bitmap = ((BitmapDrawable) upload_img.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
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
                                    storageReference.child(fileimg).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("check", String.valueOf(uri));
                                            DowloadURL = String.valueOf(uri);
                                            Log.d("check", DowloadURL);



                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            String todayString = formatter.format(Calendar.getInstance().getTime());

                                            if (barcode.length()>0 && tensach.length()>0 && tentacgia.length()>0 && Mota.length() >0 && id_theloai.length()>0
                                                    && id_ngonngu.length()>0 && NXB.length()>0 && soluong.length()>0 ) {

                                                mdata.child("Books").child(String.valueOf(id)).setValue(new SachFB(String.valueOf(id),barcode,id_theloai,id_ngonngu,tensach,tentacgia,Mota,NXB,
                                                        soluong,DowloadURL,soluong,todayString));

                                            }

                                        }
                                    });

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                /*if (barcode.length()>0 && tensach.length()>0 && tentacgia.length()>0 && Mota.length() >0 && id_theloai.length()>0
                        && id_ngonngu.length()>0 && NXB.length()>0 && soluong.length()>0 ) {
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] +System.currentTimeMillis() + "." +mangtenfile[1];
                    Log.d("FFF",file_path);
                    Log.d("FFF", String.valueOf(file));

                    final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    Log.d("TTT",requestBody.toString());
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);



                    DataClient dataClient = APIutils.getData();
                    Call<String> callback = dataClient.uploadphoto(body);



                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null) {
                                message = response.body();
                                if (message.length()>0) {
                                    Log.d("FFF",message);
                                    DataClient insertdata = APIutils.getData();
                                    retrofit2.Call<String> callback = insertdata.InsertData(tensach,barcode,tentacgia,Mota,id_ngonngu,id_theloai,NXB,
                                            soluong,APIutils.baseurl+"IMGbooks/"+message);
                                    Log.d("FFF",APIutils.baseurl+"IMGbooks/"+message);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if (result.equals("Success")){
                                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                updatemangsach();

                                                refresh();

                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Log.d("FFF",t.getMessage());

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("BBB",t.getMessage());

                        }
                    });

                }*/

                Toast.makeText(getContext(),"Thêm sách thành công",Toast.LENGTH_SHORT).show();
                edtbarcode.setText("");
                edtmt.setText("");
                edtten.setText("");
                edtnxb.setText("");
                edtsl.setText("");
                edttg.setText("");


            }

            private void GetEdtText() {
                tensach = edtten.getText().toString();
                tentacgia = edttg.getText().toString();
                barcode = edtbarcode.getText().toString();
                Mota = edtmt.getText().toString();
                id_ngonngu = String.valueOf(spinnernn.getSelectedItemId()+1);
                id_theloai = String.valueOf(spinnertheloai.getSelectedItemId()+1);
                NXB = edtnxb.getText().toString();
                soluong = edtsl.getText().toString();

            }
        });


        edtbarcode = (EditText) view.findViewById(R.id.edtbarcode);
        spinnernn = (Spinner) view.findViewById(R.id.spinnernn);
        spinnertheloai = (Spinner) view.findViewById(R.id.spinnertheloai);
        scanbt = (ImageView) view.findViewById(R.id.scanbt) ;
        upload_img = (ImageView) view.findViewById(R.id.upload_img);
        edtten = (EditText)view.findViewById(R.id.edttensach);
        edttg = (EditText) view.findViewById(R.id.edttacgia);

        edtmt = (EditText) view.findViewById(R.id.edtmota);
        edtnxb = (EditText)view.findViewById(R.id.edtnxb) ;
        edtsl = (EditText) view.findViewById(R.id.edtsoluong);
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

            }
        });
        // cancel = (Button) view.findViewById(R.id.cancel);
        //confirm = (Button) view.findViewById(R.id.confirm);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Tiếng Việt");
        arrayList.add("Tiếng Anh");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("Sách thiếu nhi");
        arrayList2.add("Giáo trình");
        arrayList2.add("Chính trị - Pháp luật");
        arrayList2.add("Văn hóa xã hội - Lịch sử");
        arrayList2.add("Truyện, Tiểu thuyết");
        arrayList2.add("Khoa học - Kinh tế");
        arrayList2.add("Văn học nghệ thuật");
        arrayList2.add("Tâm linh, Tôn giáo");
        arrayList2.add("Đời sống");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnernn.setAdapter(arrayAdapter);
        spinnertheloai.setAdapter(arrayAdapter2);




    }
    /*public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }*/
    private void initbarcode() {
        scanbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager supportFragmentManager = getFragmentManager();
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
    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(getContext(), true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                break;
            case BARCODE_READER_ACTIVITY_REQUEST:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(getContext(), "error in  scanning", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
                    Toast.makeText(getContext(), barcode.rawValue, Toast.LENGTH_SHORT).show();
                    edtbarcode.setText(barcode.rawValue);

                }
                break;
        }




    }
    private void refresh() {
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(getActivity().getIntent());
        getActivity().overridePendingTransition(0, 0);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void KeyBoardSettings() {




    }
  /*  private void updatemangsach() {
        mang_sach2.clear();


        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Sach_url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int i;

                    for (i = 0; i<response.length();i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            MainActivity.Khaibao.id_sach = jsonObject.getInt("id_sach");
                            MainActivity.Khaibao.barcode = jsonObject.getString("barcode");
                            MainActivity.Khaibao.id_ngonngu = jsonObject.getInt("id_ngonngu");
                            MainActivity.Khaibao.id_theloai = jsonObject.getInt("id_theloai");
                            MainActivity.Khaibao.tensach = jsonObject.getString("tensach");
                            MainActivity.Khaibao.tentacgia = jsonObject.getString("tentacgia");
                            MainActivity.Khaibao.NXB = jsonObject.getString("NXB");
                            MainActivity.Khaibao.Mota = jsonObject.getString("Mota");
                            MainActivity.Khaibao.soluong = jsonObject.getInt("soluong");
                            MainActivity.Khaibao.conlai = jsonObject.getInt("conlai");
                            MainActivity.Khaibao.img_sach = jsonObject.getString("img_sach");

                            mang_sach2.add(new Sach2(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                    MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                    MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,false));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getContext(), error.toString());
                Log.d("AAA", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }*/
}
