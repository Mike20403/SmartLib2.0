package com.app.smartlibhost.tableview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartlibhost.adapter.DialogAdapter;
import com.app.smartlibhost.model.Order;
import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.app.smartlibhost.R;
import com.app.smartlibhost.tableview.holder.ColumnHeaderViewHolder;
import com.app.smartlibhost.tableview.popup.ColumnHeaderLongPressPopup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;

import static com.app.smartlibhost.Fragment.fragment_history.orderlist;

/**
 * Created by evrencoskun on 2.12.2017.
 */

public class MyTableViewListener implements ITableViewListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mdata = database.getReference().child("Admin").child("Queue");
    private static final String LOG_TAG = MyTableViewListener.class.getSimpleName();
    RecyclerView rv,borrowlogs;
    private ITableView mTableView;
    ImageView Qrcode,qrimg;
    public MyTableViewListener(ITableView pTableView) {
        this.mTableView = pTableView;
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Log.d(LOG_TAG, "onCellClicked has been clicked for x= " + column + " y= " + row);
    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Log.d(LOG_TAG, "onCellLongPressed has been clicked for " + row);
        PopupMenu popupMenu = new PopupMenu(cellView.itemView.getContext(),cellView.itemView);
        popupMenu.getMenuInflater().inflate(R.menu.updatestatus,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Context context =cellView.itemView.getContext();
                switch (item.getTitle().toString()){

                    case "Chi tiết đơn mượn":
                        showdialog(orderlist.get(row),cellView.itemView.getContext());
                        break;
                    case"Đã trả":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Đã trả");
                        break;
                    case"Hoàn tất mượn":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Hoàn tất mượn");
                        break;
                    case"Sẵn sàng":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Sẵn sàng");
                        break;
                    case"Xác nhận đơn mượn":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Đã xác nhận");
                        break;
                    case"Hủy bỏ đơn mượn":
                        showAlertDialog(row,context);

                        break;


                }



                return false;
            }
        });
        popupMenu.show();

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        Log.d(LOG_TAG, "onColumnHeaderClicked has been clicked for " + column);
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        if (columnHeaderView != null && columnHeaderView instanceof ColumnHeaderViewHolder) {

            // Create Long Press Popup
            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup(
                    (ColumnHeaderViewHolder) columnHeaderView, mTableView);

            // Show
            popup.show();
        }
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        Log.d(LOG_TAG, "onRowHeaderClicked has been clicked for " + row);
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder owHeaderView, int row) {
        Log.d(LOG_TAG, "onRowHeaderLongPressed has been clicked for " + row);
        PopupMenu popupMenu = new PopupMenu(owHeaderView.itemView.getContext(),owHeaderView.itemView);
        popupMenu.getMenuInflater().inflate(R.menu.updatestatus,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Context context =owHeaderView.itemView.getContext();
                switch (item.getTitle().toString()){

                    case "Chi tiết đơn mượn":
                        showdialog(orderlist.get(row),owHeaderView.itemView.getContext());
                        break;
                    case"Đã trả":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Đã trả");
                        break;
                    case"Hoàn tất mượn":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Hoàn tất mượn");
                        break;
                    case"Sẵn sàng":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Sẵn sàng");
                        break;
                    case"Xác nhận đơn mượn":
                        mdata.child(orderlist.get(row).getKey()).child("status").setValue("Đã xác nhận");
                        break;
                    case"Hủy bỏ đơn mượn":
                        showAlertDialog(row,context);

                        break;


                }



               return false;
            }
        });
        popupMenu.show();
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
    private void showdialog(Order order,Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogorder);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        qrimg = (ImageView) dialog.findViewById(R.id.qr_img) ;

        borrowlogs = (RecyclerView) dialog.findViewById(R.id.borrowlogs);
        borrowlogs.setHasFixedSize(true);
        borrowlogs.setLayoutManager(new LinearLayoutManager(context));
        SimpleDateFormat formatter = new SimpleDateFormat();
        String borrowstr = formatter.format(order.getDate_borrowed());
        CallQRCode(order.getKey(),qrimg);
        DialogAdapter adapter = new DialogAdapter(context,order.getBorrowbooks(),borrowstr,"20/11/2019");
        borrowlogs.setAdapter(adapter);
        dialog.show();


    }

    public void showAlertDialog(int row, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Smartlib");
        builder.setMessage("Bạn có muốn xóa đơn mượn này không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Chắc chắn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String key = orderlist.get(row).getKey();
                mdata.child(key).removeValue();




                dialogInterface.dismiss();


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
