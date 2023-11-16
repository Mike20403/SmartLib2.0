package com.app.smartlibhost.tableview.holder;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import com.app.smartlibhost.R;
import com.app.smartlibhost.tableview.model.CellModel;

/**
 * Created by evrencoskun on 1.12.2017.
 */

public class CellViewHolder extends AbstractViewHolder {
    public final TextView cell_textview;
    public final LinearLayout cell_container;

    public CellViewHolder(View itemView) {
        super(itemView);
        cell_textview = itemView.findViewById(R.id.cell_data);
        cell_container = itemView.findViewById(R.id.cell_container);
    }

    public void setCellModel(CellModel p_jModel, int pColumnPosition) {

        // Change textView align by column
        cell_textview.setGravity(ColumnHeaderViewHolder.COLUMN_TEXT_ALIGNS[pColumnPosition] |
                Gravity.CENTER_VERTICAL);
        Log.d("pos",String.valueOf(pColumnPosition));
        switch (pColumnPosition){
            case 4:

                // Set text
                switch (String.valueOf(p_jModel.getData())) {
                    case "Đã khởi tạo":
                        cell_textview.setText(String.valueOf(p_jModel.getData()));
                        cell_textview.setBackgroundColor(Color.parseColor("#FFF59D"));
                        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

                        cell_textview.requestLayout();
                        break;
                    case "Đã xác nhận":
                        cell_textview.setText(String.valueOf(p_jModel.getData()));
                        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        cell_textview.setBackgroundColor(Color.parseColor("#FFF59D"));
                       cell_textview.requestLayout();
                        break;
                    case "Sẵn sàng":

                        cell_textview.setText(String.valueOf(p_jModel.getData()));
                        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        cell_textview.setBackgroundColor(Color.parseColor("#FFF59D"));
                        cell_textview.requestLayout();

                        break;
                    case "Hoàn tất mượn":
                        cell_textview.setText(String.valueOf(p_jModel.getData()));
                        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        cell_textview.setBackgroundColor(Color.parseColor("#FFF59D"));
                        cell_textview.requestLayout();



                        break;
                    case "Đã trả":

                        cell_textview.setText(String.valueOf(p_jModel.getData()));
                        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        cell_textview.setBackgroundColor(Color.parseColor("#34FA6B"));
                        cell_textview.requestLayout();



                        break;
                        default:
                            cell_textview.setText(String.valueOf(p_jModel.getData()));
                            cell_textview.setBackgroundResource(R.color.yellow);


                }
                break;

                default:
                    cell_textview.setText(String.valueOf(p_jModel.getData()));
                    // It is necessary to remeasure itself.
                    cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    cell_textview.requestLayout();




        }


    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        super.setSelected(p_nSelectionState);

        if (p_nSelectionState == SelectionState.SELECTED) {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .selected_text_color));
        } else {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .unselected_text_color));
        }
    }
}
