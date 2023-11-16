package com.app.smartlibhost.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.app.smartlibhost.R;
import com.app.smartlibhost.model.Order;
import com.app.smartlibhost.tableview.MyTableAdapter;
import com.app.smartlibhost.tableview.MyTableViewListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragment_history extends Fragment {
    View view;
    private TableView mTableView;
    private MyTableAdapter mTableAdapter;
    private ProgressBar mProgressBar;
    public static ArrayList<Order> orderlist = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getReference().child("Admin").child("Queue");
    int t = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history,container,false);
        Anhxa();

        initializeTableView(mTableView);
        getTableData();


        return view;
    }
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTableView.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mTableView.setVisibility(View.VISIBLE);
    }
    private void getTableData() {
        showProgressBar();
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                orderlist.add(dataSnapshot.getValue(Order.class));
                mTableAdapter.setUserList(orderlist);
                hideProgressBar();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Testttt",dataSnapshot.toString());
                    Order order = dataSnapshot.getValue(Order.class);


                //initializeTableView(mTableView);;
                int k = findObjectId(dataSnapshot);
                if (k == -1) {

            }else{
                    orderlist.set(k,order);
                    Log.d("Testtt",orderlist.get(k).getStatus());
                }

               // initializeTableView(mTableView);
                mTableAdapter.setUserList(orderlist);
                hideProgressBar();


            }



            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

               int k = findObjectId(dataSnapshot);
              if (k !=-1){
                  orderlist.remove(k);
              }

                //initializeTableView(mTableView);
                mTableAdapter.setUserList(orderlist);
                hideProgressBar();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int findObjectId(DataSnapshot dataSnapshot) {
        t = -1; Order order = dataSnapshot.getValue(Order.class);
        for (int i = 0; i<orderlist.size(); i++){
            Order order2 = orderlist.get(i) ;
            if (order.getKey().matches(order2.getKey())  ){
                t =i;
                return t;
            }
        }
        return t;
    }

    public fragment_history() {
    }

    private void initializeTableView(TableView tableView){
        // Create TableView Adapter
        orderlist.clear();
        mTableAdapter = new MyTableAdapter(getContext());
        tableView.setAdapter(mTableAdapter);

        // Create listener
        tableView.setTableViewListener(new MyTableViewListener(tableView));

    }

    private void Anhxa() {
        mTableView = view.findViewById(R.id.my_TableView);
        mProgressBar = view.findViewById(R.id.progressBar);
    }


}
