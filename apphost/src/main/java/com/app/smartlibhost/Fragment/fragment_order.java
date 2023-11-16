package com.app.smartlibhost.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.DialogAdapter;
import com.app.smartlibhost.adapter.ViewHolder.TimeLineAdapter;
import com.app.smartlibhost.model.Order;
import com.app.smartlibhost.model.OrderStatus;
import com.app.smartlibhost.model.Orientation;
import com.app.smartlibhost.model.TimeLineModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class fragment_order extends Fragment {
        View view;
    RecyclerView rv,borrowlogs;
    ImageView emptycart;
    TextView note;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String  UId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference admdata = database.getReference().child("Admin").child("Queue");
    TextView status,date,orderkey,name,dateborrow,datepayback,price;
    ImageView Qrcode,qrimg;
    ChildEventListener childEventListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order,container,false);
        Anhxa();
        GetCurrentOrderStatus();



        return view;
    }

    private void GetCurrentOrderStatus() {
       childEventListener = admdata.orderByChild("customer_UID").equalTo(UId).limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order morder = dataSnapshot.getValue(Order.class);
                String status = morder.getStatus();
                initView(status);
                Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
                if (!status.isEmpty()) {
                    Log.d("Errorr","Not Empty");
                    emptycart.setVisibility(View.INVISIBLE);
                    note.setVisibility(View.INVISIBLE);
                    settv(morder);
                    Qrcode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showdialog(morder);

                        }
                    });

                    //rv.setVisibility(View.GONE);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order morder = dataSnapshot.getValue(Order.class);
                String status = morder.getStatus();
                initView(status);
                settv(morder);


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
    private void settv(Order morder) {

        switch (morder.getStatus()){
            case "Đã trả" :
                status.setTextColor(Color.GREEN);
                break;
            default:



        }
        status.setText(morder.getStatus());
        CallQRCode(morder.getKey(),Qrcode);
        SimpleDateFormat formatter = new SimpleDateFormat();
        String datestring = formatter.format(morder.getDate_borrowed());
        date.setText(datestring);
        orderkey.setText(morder.getKey());
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
    private void showdialog(Order order) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialogorder);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        qrimg = (ImageView) dialog.findViewById(R.id.qr_img) ;

        borrowlogs = (RecyclerView) dialog.findViewById(R.id.borrowlogs);
        borrowlogs.setHasFixedSize(true);
        borrowlogs.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleDateFormat formatter = new SimpleDateFormat();
        String borrowstr = formatter.format(order.getDate_borrowed());
        CallQRCode(order.getKey(),qrimg);
        DialogAdapter adapter = new DialogAdapter(getContext(),order.getBorrowbooks(),borrowstr,"20/11/2019");
        borrowlogs.setAdapter(adapter);
        dialog.show();


    }

    private void Anhxa() {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setHasFixedSize(true);
        emptycart = (ImageView) view.findViewById(R.id.emptycart);
        note = (TextView) view.findViewById(R.id.note);
        status = (TextView) view.findViewById(R.id.status);
        Qrcode = (ImageView) view.findViewById(R.id.qrcode);
        date = (TextView) view.findViewById(R.id.date);
        orderkey = (TextView) view.findViewById(R.id.orderkey);

    }
    private void initView(String statuss) {
        setDataListItems(statuss);
        mTimeLineAdapter = new TimeLineAdapter(mDataList, Orientation.VERTICAL, false);
        rv.setAdapter(mTimeLineAdapter);


    }

    private void setDataListItems(String status){
        mDataList.clear();
        switch (status)
        {
            case "Đã khởi tạo":
                mDataList.add(new TimeLineModel("Hoàn tất trả sách và nhận lại tiền cọc","","2019-12-11 12:00",OrderStatus.INACTIVE,R.drawable.refund));
                mDataList.add(new TimeLineModel("Hoàn tất thủ tục mượn sách","Remember to bring back your books on time !","2019-12-11 10:00",OrderStatus.INACTIVE,R.drawable.success));
                mDataList.add(new TimeLineModel("Sách đã sẵn sàng","Your books are ready, come and get it.","2019-12-11 08:00", OrderStatus.INACTIVE,R.drawable.comeandget));
                mDataList.add(new TimeLineModel("Đang chuẩn bị sách","We are preparing your books", "2019-12-10 15:00", OrderStatus.INACTIVE,R.drawable.orderprocess));
                mDataList.add(new TimeLineModel("Đơn mượn đã được xác nhận","Your Order has been confirmed by librarian", "2019-02-10 14:30", OrderStatus.INACTIVE,R.drawable.orderconfirm));
                mDataList.add(new TimeLineModel("Đã tạo đơn mượn","We have received your order","2019-12-10 12:30",OrderStatus.COMPLETED,R.drawable.orderplaced));

                break;
            case "Đã xác nhận"  :
                mDataList.add(new TimeLineModel("Hoàn tất trả sách và nhận lại tiền cọc","","2019-12-13 12:00",OrderStatus.INACTIVE,R.drawable.refund));
                mDataList.add(new TimeLineModel("Hoàn tất thủ tục mượn sách","Remember to bring back your books on time !","2019-12-13 10:00",OrderStatus.INACTIVE,R.drawable.success));
                mDataList.add(new TimeLineModel("Sách đã sẵn sàng","Your books are ready, come and get it.","2019-12-11 08:00", OrderStatus.INACTIVE,R.drawable.comeandget));
                mDataList.add(new TimeLineModel("Đang chuẩn bị sách","We are preparing your books", "2019-12-11 15:00", OrderStatus.ACTIVE,R.drawable.orderprocess));
                mDataList.add(new TimeLineModel("Đơn mượn đã được xác nhận","Your Order has been confirmed by librarian", "2019-12-10 14:30", OrderStatus.COMPLETED,R.drawable.orderconfirm));
                mDataList.add(new TimeLineModel("Đã tạo đơn mượn","We have received your order","2019-12-10 12:30",OrderStatus.COMPLETED,R.drawable.orderplaced));
                break;
            case "Sẵn sàng":
                mDataList.add(new TimeLineModel("Hoàn tất trả sách và nhận lại tiền cọc","","2019-12-14 12:00",OrderStatus.INACTIVE,R.drawable.refund));
                mDataList.add(new TimeLineModel("Hoàn tất thủ tục mượn sách","Remember to bring back your books on time !","2019-12-14 10:00",OrderStatus.INACTIVE,R.drawable.success));
                mDataList.add(new TimeLineModel("Sách đã sẵn sàng","Your books are ready, come and get it.","2019-12-13 08:00", OrderStatus.ACTIVE,R.drawable.comeandget));
                mDataList.add(new TimeLineModel("Đang chuẩn bị sách","We are preparing your books", "2019-12-13 15:00", OrderStatus.COMPLETED,R.drawable.orderprocess));
                mDataList.add(new TimeLineModel("Đơn mượn đã được xác nhận","Your Order has been confirmed by librarian", "2019-12-12 14:30", OrderStatus.COMPLETED,R.drawable.orderconfirm));
                mDataList.add(new TimeLineModel("Đã tạo đơn mượn","We have received your order","2019-12-12 12:30",OrderStatus.COMPLETED,R.drawable.orderplaced));

                break;
            case "Hoàn tất mượn" :
                mDataList.add(new TimeLineModel("Hoàn tất trả sách và nhận lại tiền cọc","","",OrderStatus.ACTIVE,R.drawable.refund));
                mDataList.add(new TimeLineModel("Hoàn tất thủ tục mượn sách","Remember to bring back your books on time !","2019-12-11 10:00",OrderStatus.COMPLETED,R.drawable.success));
                mDataList.add(new TimeLineModel("Sách đã sẵn sàng","Your books are ready, come and get it.","2019-12-11 08:00", OrderStatus.COMPLETED,R.drawable.comeandget));
                mDataList.add(new TimeLineModel("Đang chuẩn bị sách","We are preparing your books", "2019-12-1 15:00", OrderStatus.COMPLETED,R.drawable.orderprocess));
                mDataList.add(new TimeLineModel("Đơn mượn đã được xác nhận","Your Order has been confirmed by librarian", "2019-02-10 14:30", OrderStatus.COMPLETED,R.drawable.orderconfirm));
                mDataList.add(new TimeLineModel("Đã tạo đơn mượn","We have received your order","2019-12-10 12:30",OrderStatus.COMPLETED,R.drawable.orderplaced));

                break;
            case "Đã trả":
                mDataList.add(new TimeLineModel("Hoàn tất trả sách và nhận lại tiền cọc","","2019-12-11 12:00",OrderStatus.COMPLETED,R.drawable.refund));
                mDataList.add(new TimeLineModel("Hoàn tất thủ tục mượn sách","Remember to bring back your books on time !","2019-12-11 10:00",OrderStatus.COMPLETED,R.drawable.success));
                mDataList.add(new TimeLineModel("Sách đã sẵn sàng","Your books are ready, come and get it.","2019-12-11 08:00", OrderStatus.COMPLETED,R.drawable.comeandget));
                mDataList.add(new TimeLineModel("Đang chuẩn bị sách","We are preparing your books", "2019-12-8 15:00", OrderStatus.COMPLETED,R.drawable.orderprocess));
                mDataList.add(new TimeLineModel("Đơn mượn đã được xác nhận","Your Order has been confirmed by librarian", "2019-12-7 14:30", OrderStatus.COMPLETED,R.drawable.orderconfirm));
                mDataList.add(new TimeLineModel("Đã tạo đơn mượn","We have received your order","2019-12-7 12:30",OrderStatus.COMPLETED,R.drawable.orderplaced));


                break;

                default:
                    Log.d("Errorr","Lỗi");
                    emptycart.setVisibility(View.VISIBLE);
                    note.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);




        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        admdata.removeEventListener(childEventListener);
    }
}
