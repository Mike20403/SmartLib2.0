package com.app.smartlibhost.tableview.holder;

import android.view.View;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;


import com.app.smartlibhost.tableview.model.CellModel;

/**
 * Created by evrencoskun on 4.12.2017.
 */

public class StatusCellViewHolder extends AbstractViewHolder {

   // private final ImageButton cell_image_button;

   // private final Drawable cell_male_drawable;
   // private final Drawable cell_female_drawable;

    public StatusCellViewHolder(View itemView) {
        super(itemView);
       // cell_image_button =  itemView.findViewById(R.id.cell_image_button);

        // Get vector drawables
      //  cell_male_drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.male);
      //  cell_female_drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.female);
    }

    public void setCellModel(CellModel p_jModel) {
        char c = String.valueOf(p_jModel.getData()).trim().charAt(0);

        if (c == 'F') {
          //  cell_image_button.setImageDrawable(cell_female_drawable);
        } else if (c == 'M') {
          //  cell_image_button.setImageDrawable(cell_male_drawable);
        }
    }
}
