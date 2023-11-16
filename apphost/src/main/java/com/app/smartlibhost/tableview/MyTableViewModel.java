package com.app.smartlibhost.tableview;

import android.view.Gravity;

import com.app.smartlibhost.model.Order;
import com.app.smartlibhost.tableview.model.CellModel;
import com.app.smartlibhost.tableview.model.ColumnHeaderModel;
import com.app.smartlibhost.tableview.model.RowHeaderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MyTableViewModel {
    // View Types
    public static final int STATUS_TYPE = 0;
    public static final int MONEY_TYPE = 0;

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    public int getCellItemViewType(int column) {

        switch (column) {
            case 5:
                // 5. column header is gender.
                return STATUS_TYPE;
            case 8:
                // 8. column header is Salary.
                return MONEY_TYPE;
            default:
                return 0;
        }
    }

     /*
       - Each of Column Header -
            "Id"
            "Name"
            "Nickname"
            "Email"
            "Birthday"
            "Gender"
            "Age"
            "Job"
            "Salary"
            "CreatedAt"
            "UpdatedAt"
            "Address"
            "Zip Code"
            "Phone"
            "Fax"
     */

    public int getColumnTextAlign(int column) {
        switch (column) {
            // Id
            case 0:
                return Gravity.CENTER;
            // Name
            case 1:
                return Gravity.LEFT;
            // Nickname
            case 2:
                return Gravity.LEFT;
            // Email
            case 3:
                return Gravity.LEFT;
            // BirthDay
            case 4:
                return Gravity.CENTER;
            // Gender (Sex)
            case 5:
                return Gravity.CENTER;
            // Age
            case 6:
                return Gravity.CENTER;
            // Job
            case 7:
                return Gravity.LEFT;
            // Salary
            case 8:
                return Gravity.CENTER;
            // CreatedAt
            case 9:
                return Gravity.CENTER;
            // UpdatedAt
            case 10:
                return Gravity.CENTER;
            // Address
            case 11:
                return Gravity.LEFT;
            // Zip Code
            case 12:
                return Gravity.RIGHT;
            // Phone
            case 13:
                return Gravity.RIGHT;
            // Fax
            case 14:
                return Gravity.RIGHT;
            default:
                return Gravity.CENTER;
        }

    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Mã đơn mượn"));
        list.add(new ColumnHeaderModel("Mã thẻ thư viện"));
        list.add(new ColumnHeaderModel("Ngày mượn"));
        list.add(new ColumnHeaderModel("Ngày trả"));
        list.add(new ColumnHeaderModel("Trạng thái"));
       // list.add(new ColumnHeaderModel("Sex"));
       // list.add(new ColumnHeaderModel("Age"));
       // list.add(new ColumnHeaderModel("Job"));
      //  list.add(new ColumnHeaderModel("Salary"));
       // list.add(new ColumnHeaderModel("CreatedAt"));
      //  list.add(new ColumnHeaderModel("UpdatedAt"));
      //  list.add(new ColumnHeaderModel("Address"));
      //  list.add(new ColumnHeaderModel("Zip Code"));
      //  list.add(new ColumnHeaderModel("Phone"));
      //  list.add(new ColumnHeaderModel("Fax"));

        return list;
    }
    public static String getFormattedDate() {
        //SimpleDateFormat called without pattern
        return new SimpleDateFormat().format(Calendar.getInstance().getTime());
    }

    private List<List<CellModel>> createCellModelList(List<Order> orderList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from User list for Cell Items
        // In this example, User list is populated from web service

        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
            String todayString = formatter.format(order.getDate_borrowed());
            List<CellModel> list = new ArrayList<>();
            String datepayback;
            if (order.getDate_payback() != null){
                 datepayback = formatter.format(order.getDate_payback());
            }else{
                 datepayback = "Chưa trả";
            }


            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, order.getKey()));          // "Id"
            list.add(new CellModel("2-" + i, order.getCustomer_UID())); // "Customer_UID"


            list.add(new CellModel("3-" + i, todayString));    // "Date Borrowed"
            list.add(new CellModel("4-" + i, datepayback));       // "Date Payback"
            list.add(new CellModel("5-" + i, order.getStatus()));   // "Status"
          //  list.add(new CellModel("6-" + i, user.gender));      // "Gender"
           // list.add(new CellModel("7-" + i, user.age));         // "Age"
           // list.add(new CellModel("8-" + i, user.job));         // "Job"
          //  list.add(new CellModel("9-" + i, user.salary));      // "Salary"
          //  list.add(new CellModel("10-" + i, user.created_at)); // "CreatedAt"
          //  list.add(new CellModel("11-" + i, user.updated_at)); // "UpdatedAt"
           // list.add(new CellModel("12-" + i, user.address));    // "Address"
          //  list.add(new CellModel("13-" + i, user.zipcode));    // "Zip Code"
          //  list.add(new CellModel("14-" + i, user.mobile));     // "Phone"
          //  list.add(new CellModel("15-" + i, user.fax));        // "Fax"

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }


    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }


    public void generateListForTableView(List<Order> users) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(users);
        mRowHeaderModelList = createRowHeaderList(users.size());
    }


}



