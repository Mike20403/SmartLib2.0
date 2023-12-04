package com.app.smartlibhost.Fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.app.smartlibhost.R;
import com.app.smartlibhost.activity.MainActivity;
import com.app.smartlibhost.adapter.sach_adapter;
import com.app.smartlibhost.adapter.sach_adapterv2;
import com.app.smartlibhost.model.SachFB;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter;


public class fragment_scrollview<context> extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    TextView tvmain;
    MainActivity context2;
    fragment_banner contextbanner;
    Context context;
    public static NestedScrollView scrollView;
    public static RecyclerView recyclerView,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9;
    public static ArrayList<SachFB> mangsach;
    public static ArrayList<SachFB> ms1;
    public static ArrayList<SachFB> ms2;
    public static ArrayList<SachFB> ms3;
    public static ArrayList<SachFB> ms4;
    public static ArrayList<SachFB> ms5;
    public static ArrayList<SachFB> ms6;
    public static ArrayList<SachFB> ms7;
    public static ArrayList<SachFB> ms8;
    public static ArrayList<SachFB> ms9;
    public static sach_adapterv2 sach_adapter;
    public static sach_adapter sach_adapter2;
    public static sach_adapter sach_adapter3;
    public static sach_adapter sach_adapter4;
    public static sach_adapter sach_adapter5;
    public static sach_adapter sach_adapter1;
    public static sach_adapter sach_adapter6;
    public static sach_adapter sach_adapter7;
    public static sach_adapter sach_adapter8;
    public static sach_adapter sach_adapter9;
    FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdata = mdatabase.getReference();
    public static ArrayList<SachFB> array = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
                      Anhxa();
                      GetData();
                      //ActionViewFlipper();
                     // Getsachmoi();
                      SetRecyclerView();
               scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });

            return  view;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }

    private void SetRecyclerView() {
        recyclerView = view.findViewById(R.id.recycleviewmain);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setHasFixedSize(true);
        sach_adapter = new sach_adapterv2(getActivity(),mangsach);
        recyclerView.setAdapter(sach_adapter);
        sach_adapter.notifyDataSetChanged();


        rv1 = view.findViewById(R.id.rv1);
        rv1.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv1, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv1.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv1.setHasFixedSize(true);

        sach_adapter1 = new sach_adapter(ms1,getActivity());

        rv1.setAdapter(sach_adapter1);
        sach_adapter1.notifyDataSetChanged();

        rv2 = view.findViewById(R.id.rv2);
        rv2.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv2, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv2.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv2.setHasFixedSize(true);
        sach_adapter2 = new sach_adapter(ms2,getActivity());

        rv2.setAdapter(sach_adapter2);
        sach_adapter2.notifyDataSetChanged();

        rv3 = view.findViewById(R.id.rv3);
        rv3.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv3, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv3.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv3.setHasFixedSize(true);
        sach_adapter3 = new sach_adapter(ms3,getActivity());

        rv3.setAdapter(sach_adapter3);
        sach_adapter3.notifyDataSetChanged();

        rv4= view.findViewById(R.id.rv4);
        rv4.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv4, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv4.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv4.setHasFixedSize(true);
        sach_adapter4 = new sach_adapter(ms4,getActivity());
        rv4.setAdapter(sach_adapter4);
        sach_adapter4.notifyDataSetChanged();

        rv5 = view.findViewById(R.id.rv5);
        rv5.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv5, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv5.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv5.setHasFixedSize(true);
        sach_adapter5 = new sach_adapter(ms5,getActivity());
        rv5.setAdapter(sach_adapter5);
        sach_adapter5.notifyDataSetChanged();

        rv6 = view.findViewById(R.id.rv6);
        rv6.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv6, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv6.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv6.setHasFixedSize(true);
        sach_adapter6 = new sach_adapter(ms6,getActivity());
        rv6.setAdapter(sach_adapter6);
        sach_adapter6.notifyDataSetChanged();

        rv7 = view.findViewById(R.id.rv7);
        rv7.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv7, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv7.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv7.setHasFixedSize(true);
        sach_adapter7 = new sach_adapter(ms7,getActivity());
        rv7.setAdapter(sach_adapter7);
        sach_adapter7.notifyDataSetChanged();

        rv8 = view.findViewById(R.id.rv8);
        rv8.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv8, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv8.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv8.setHasFixedSize(true);
        sach_adapter8 = new sach_adapter(ms8,getActivity());
        rv8.setAdapter(sach_adapter8);
        sach_adapter8.notifyDataSetChanged();

        rv9 = view.findViewById(R.id.rv9);
        rv9.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rv9, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rv9.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv9.setHasFixedSize(true);
        sach_adapter9 = new sach_adapter(ms9,getActivity());
        rv9.setAdapter(sach_adapter9);
        sach_adapter9.notifyDataSetChanged();
    }
    private void GetData() {
        array.clear();

        mdata.child("Books").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SachFB sach = dataSnapshot.getValue(SachFB.class);

                Log.d("Data2",dataSnapshot.getValue().toString());
                array.add(sach);
                Log.d("Data2",""+(array.size()));
                getResponse();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                SachFB sach = dataSnapshot.getValue(SachFB.class);

                Log.d("Data2",dataSnapshot.getValue().toString());
                array.remove(sach);
                Log.d("Data2",""+(array.size()));
                getResponse();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getResponse(){
        ms1.clear();ms2.clear();ms3.clear();ms4.clear();ms5.clear();ms6.clear();ms7.clear();ms8.clear();ms9.clear();
        mangsach.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(Calendar.getInstance().getTime());
                if (array!= null){
                    if (array.size() >0) {
                        for (int i =array.size()-1 ; i>=0;i--) {
                            SachFB sach = array.get(i);
                            MainActivity.Khaibao.id_sach = sach.getId_sach();
                            MainActivity.Khaibao.barcode = sach.getBarcode();
                            MainActivity.Khaibao.id_ngonngu = sach.getId_ngonngu();
                            MainActivity.Khaibao.id_theloai = sach.getId_theloai();
                            MainActivity.Khaibao.tensach = sach.getTensach();
                            MainActivity.Khaibao.tentacgia = sach.getTentacgia();
                            MainActivity.Khaibao.NXB = sach.getNXB();
                            MainActivity.Khaibao.Mota = sach.getMota();
                            MainActivity.Khaibao.soluong = sach.getSoluong();
                            MainActivity.Khaibao.conlai = sach.getConlai();
                            MainActivity.Khaibao.img_sach = sach.getImg_sach();

                            mangsach.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                    MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                    MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));
                            sach_adapter.notifyDataSetChanged();


                            switch ( MainActivity.Khaibao.id_theloai) {
                                case "1":
                                    ms1.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case "2" :
                                    ms2.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case "3":
                                    ms3.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "4":
                                    ms4.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "5":
                                    ms5.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "6":
                                    ms6.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "7":
                                    ms7.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "8":
                                    ms8.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                case  "9":
                                    ms9.add(new SachFB(MainActivity.Khaibao.id_sach,MainActivity.Khaibao.barcode, MainActivity.Khaibao.id_theloai, MainActivity.Khaibao.id_ngonngu,
                                            MainActivity.Khaibao.tensach, MainActivity.Khaibao.tentacgia, MainActivity.Khaibao.Mota, MainActivity.Khaibao.NXB,
                                            MainActivity.Khaibao.soluong, MainActivity.Khaibao.img_sach, MainActivity.Khaibao.conlai,array.get(i).getDate_added()));

                                    break;
                                default:

                            }
                            sach_adapter2.notifyDataSetChanged();
                            sach_adapter1.notifyDataSetChanged();
                            sach_adapter3.notifyDataSetChanged();
                            sach_adapter4.notifyDataSetChanged();
                            sach_adapter5.notifyDataSetChanged();
                            sach_adapter6.notifyDataSetChanged();
                            sach_adapter7.notifyDataSetChanged();
                            sach_adapter8.notifyDataSetChanged();
                            sach_adapter9.notifyDataSetChanged();
                        }
                    }
                }
    }


    private void Anhxa() {
    //    viewFlipper = (ViewFlipper) view.findViewById(R.id.viewflippermain);
        tvmain = (TextView) view.findViewById(R.id.tvmain);
        mangsach = new ArrayList<SachFB>();
        ms1 = new ArrayList<>();
        ms2 = new ArrayList<>();
        ms3 = new ArrayList<>();
        ms4 = new ArrayList<>();
        ms5 = new ArrayList<>();
        ms6 = new ArrayList<>();
        ms7 = new ArrayList<>();
        ms8 = new ArrayList<>();
        ms9 = new ArrayList<>();


        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(scrollView));

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



}
