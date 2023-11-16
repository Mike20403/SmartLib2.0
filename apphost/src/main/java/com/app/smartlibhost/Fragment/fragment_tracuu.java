package com.app.smartlibhost.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.lv_adapter;
import com.app.smartlibhost.model.Sach2;
import com.app.smartlibhost.model.SachFB;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class fragment_tracuu extends Fragment {
    View view;
    ImageView search;
    public static EditText editText;
     lv_adapter lv_apdapter;

    public static ArrayList<Sach2> mang_sach2;
    ListView lvsearch;
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdata = mdatabase.getReference();
    fragment_tracuu context2;
    public static ArrayList<SachFB> mang_sach = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);


        Anhxa();
        GetData();



                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text = s.toString().trim();



                        if (!TextUtils.isEmpty(s)) {

                            lvsearch.setVisibility(View.VISIBLE);

                        } else {

                            lvsearch.setVisibility(View.GONE);

                        }

                        lv_apdapter.getFilter().filter(s);


                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        lv_apdapter.getFilter().filter(s);


                    }
                });





        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        //do something
                        hideKeyboard(getActivity());
                        editText.clearFocus();



                        //true because you handle the event
                        return true;
                    }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(getActivity());
                }
            }
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearFocus();


                hideKeyboard((AppCompatActivity) getActivity());

            }
        });






       return view;

    }

    private void GetData() {

        mdata.child("Books").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SachFB sach = dataSnapshot.getValue(SachFB.class);

                Log.d("Data",dataSnapshot.getValue().toString());
                mang_sach.add(sach);
                mang_sach2.add(new Sach2(sach.getId_sach(), sach.getBarcode(), sach.getId_theloai(), sach.getId_ngonngu(),
                                sach.getTensach(), sach.getTentacgia(), sach.getMota(), sach.getNXB(),
                                sach.getSoluong(), sach.getImg_sach(), sach.getConlai(), Boolean.FALSE));
                lv_apdapter.notifyDataSetChanged();



            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void hideKeyboard(FragmentActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


  /*  private void Getsachmoi() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Sach_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null) {

                    for (int i = 0; i<response.length();i++)
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
                            mang_sach2.add(new Sach2(MainActivity.Khaibao.id_sach, MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                    MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                    MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai, Boolean.FALSE));

                            mang_sach.add(new Sach(MainActivity.Khaibao.id_sach, MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                    MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                    MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai));
                            lv_apdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getContext(),error.toString());
                Log.d("AAA",error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);


    }*/







    private void Anhxa() {

        mang_sach = new ArrayList<SachFB>();
        mang_sach2 = new ArrayList<Sach2>();
        lv_apdapter = new lv_adapter(getActivity(),R.layout.dong_recycle,mang_sach);
        editText = (EditText) view.findViewById(R.id.editText);
        search = (ImageView) view.findViewById(R.id.search);
        search.setImageResource(R.drawable.loupe);
        lvsearch = (ListView) view.findViewById(R.id.lv_search);
        lvsearch.setAdapter(lv_apdapter);
        lv_apdapter.notifyDataSetChanged();




    }



}
