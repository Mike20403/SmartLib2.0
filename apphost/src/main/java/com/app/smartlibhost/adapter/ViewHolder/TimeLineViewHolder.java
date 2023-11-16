package com.app.smartlibhost.adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.smartlibhost.R;
import com.github.vipulasri.timelineview.TimelineView;


/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    // Use standard Android view binding instead of ButterKnife
    protected TextView mDate;
    protected TextView mMessage;
    protected TimelineView mTimelineView;
    protected ImageView status_img;
    protected TextView statustitle;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        mDate = itemView.findViewById( R.id.text_timeline_date);
        mMessage = itemView.findViewById(R.id.text_timeline_title);
        mTimelineView = itemView.findViewById(R.id.time_marker);
        status_img = itemView.findViewById(R.id.img_status);
        statustitle = itemView.findViewById(R.id.statustitle);

        mTimelineView.initLine(viewType);
    }
}
