package com.app.smartlibhost.Fragment;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.adapter.sach_adapter3;
import com.app.smartlibhost.model.Sach2;

import java.util.ArrayList;
import java.util.List;

import static com.app.smartlibhost.Fragment.fragment_tracuu.mang_sach2;

public class fragment_addsl extends Fragment {
    View view;
    ImageView delete;
    RecyclerView rv;
    TableLayout tableLayout;





    Button bt,bt2;

    sach_adapter3 sach_adapter3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addsl,container,false);
        Anhxa2();
        setuplv();

        return view;
    }

    private void setuplv() {
        rv = (RecyclerView) view.findViewById(R.id.chonsach);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        sach_adapter3 = new sach_adapter3(getContext(),mang_sach2);
        rv.setHasFixedSize(true);
        rv.setAdapter(sach_adapter3);

        sach_adapter3.notifyDataSetChanged();
    }


    private void Anhxa2() {
        bt2 = (Button) view.findViewById(R.id.btadd);

        tableLayout = (TableLayout) view.findViewById(R.id.tablelayout);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Sach2> arraybook = new ArrayList<>();



                for (int i=0; i< sach_adapter3.arraysach.size(); i++ ) {
                    if (sach_adapter3.arraysach.get(i).getCheckedstate()) {
                        arraybook.add(mang_sach2.get(i));
                    }
                }




                List<TableRow> rows = new ArrayList<>();
                if (tableLayout.getChildCount() != 0) {
                    tableLayout.removeViews(1,tableLayout.getChildCount()-1);
                    Log.d("LLL",tableLayout.getChildCount()+"");

                }

                for(int i = 0; i <arraybook.size() ; i++)
                {



                    Log.d("TTT",i+"");
                    Sach2 sach2 = arraybook.get(i);
                    final TableRow row = (TableRow)LayoutInflater.from(getContext()).inflate(R.layout.dong_table, null);

                    ((TextView)row.findViewById(R.id.stt)).setText(String.valueOf(i+1));
                    ((TextView)row.findViewById(R.id.tensach)).setText(sach2.getTensach());
                    row.setId(i);
                    rows.add(row);
                    tableLayout.addView( rows.get(i));

                    final ImageButton delete = rows.get(i).findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tableLayout.removeView((View) delete.getParent());
                            String  txt =  ((TextView)((View) v.getRootView().findViewById(R.id.tensach))).getText().toString();
                            Log.d("TTT",txt);
                            updatearray(txt);
                        }

                        private void updatearray(String text) {
                            arraybook.clear();
                            for (int i=0; i< sach_adapter3.arraysach.size(); i++ ) {
                                if (sach_adapter3.arraysach.get(i).getCheckedstate() && sach_adapter3.arraysach.get(i).getTensach().equals(text)) {
                                    sach_adapter3.arraysach.get(i).setCheckedstate(false);
                                    sach_adapter3.notifyDataSetChanged();
                                }else {
                                    arraybook.add(mang_sach2.get(i));
                                }
                            }


                        }
                    });

                }


                tableLayout.requestLayout();



            }



        });








    }



}
