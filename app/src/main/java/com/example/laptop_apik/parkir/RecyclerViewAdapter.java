package com.example.laptop_apik.parkir;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Laptop_Apik on 7/13/2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    Context context;
    List<SpotDetails> MainImageUploadInfoList;
    private int row_index;

    public RecyclerViewAdapter(Context context, List<SpotDetails>TempList){
        this.MainImageUploadInfoList=TempList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SpotDetails spotDetails = MainImageUploadInfoList.get(position);

        holder.SpotNameTextView.setText  ("Nama    : "+spotDetails.getName());
        holder.SpotStatusTextView.setText("Status   : "+spotDetails.getStatus());

        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 row_index = position;
                notifyDataSetChanged();
            }
        });
        if (row_index==position){
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#38d6b6"));
        } else {
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#f7f3f3"));
        }

    }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView SpotNameTextView;
        public TextView SpotStatusTextView;
        LinearLayout row_linearlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            row_linearlayout=(LinearLayout)itemView.findViewById(R.id.row_linrLayout);
            SpotNameTextView    =(TextView) itemView.findViewById(R.id.TextViewSpotName);
            SpotStatusTextView  =(TextView) itemView.findViewById(R.id.TextViewSpotStatus);
        }
    }

    public class ItemDecoration {
    }
}
