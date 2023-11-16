package com.app.smartlibhost.adapter.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartlibhost.R;
import com.app.smartlibhost.model.OrderStatus;
import com.app.smartlibhost.model.Orientation;
import com.app.smartlibhost.model.TimeLineModel;
import com.app.smartlibhost.ultil.DateTimeUtils;
import com.app.smartlibhost.ultil.VectorDrawableUtils;
import com.github.vipulasri.timelineview.TimelineView;


import java.util.List;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        if (mOrientation == Orientation.HORIZONTAL) {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_horizontal_line_padding : R.layout.item_timeline_horizontal, parent, false);
        } else {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline, parent, false);
        }

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);

        if(timeLineModel.getStatus() == OrderStatus.INACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            holder.mTimelineView.setStartLine(Color.parseColor("#70D5D5D5"),getItemViewType(position));
            holder.mTimelineView.setEndLine(Color.parseColor("#70D5D5D5"),getItemViewType(position));
            holder.mDate.setTextColor(Color.GRAY);
            holder.mMessage.setTextColor(Color.GRAY);
            holder.statustitle.setTextColor(Color.GRAY);

        } else if(timeLineModel.getStatus() == OrderStatus.ACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.yellow));
                holder.mTimelineView.setStartLine(Color.GRAY,getItemViewType(position));
             //   holder.mTimelineView.setEndLine(Color.GREEN,getItemViewType(position));
           holder.mDate.setTextColor(Color.parseColor("#FFF7D23E"));
           holder.mMessage.setTextColor(Color.parseColor("#FFF7D23E"));
           holder.statustitle.setTextColor(Color.parseColor("#FFF7D23E"));
           // holder.mDate.setTextColor(Color.GREEN);
           // holder.mMessage.setTextColor(Color.GREEN);
           // holder.statustitle.setTextColor(Color.GREEN);


        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
            holder.mDate.setTextColor(Color.RED);
            holder.mMessage.setTextColor(Color.RED);
            holder.statustitle.setTextColor(Color.RED);

        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));

        }
        else
            holder.mDate.setVisibility(View.GONE);
            holder.statustitle.setText(timeLineModel.getStatustitle());
            holder.status_img.setImageResource(timeLineModel.getStatus_img());
        holder.mMessage.setText(timeLineModel.getMessage());
    }


    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}