package com.test.test.testprojand.RecyclerViewPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.test.testprojand.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;

    private List<ModelRecyclerView> rowValues;
//    ItemClickInterface itemClick;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public RecyclerViewAdapter(Context context, List<ModelRecyclerView> rowValues) {
        this.context = context;
        this.rowValues = rowValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        TextView lblMainData;
        TextView lblDetails1;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            lblMainData = v.findViewById(R.id.lblMainData);
            lblDetails1 = v.findViewById(R.id.lblDetailsData1);

//            layout.setOnClickListener(this::onClick);
//            layout.setOnLongClickListener(this::onLongClick);
        }

//        @Override
//        public void onClick(View v) {
//            System.out.println("Click");
//            if (itemClick != null) itemClick.onClick(v, getAdapterPosition());
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            System.out.println("Long Click");
//            if (itemClick != null) itemClick.onLongClick(v, getAdapterPosition());
//            return true;
//        }
    }

//    public void setClickListener(ItemClickInterface itemClick) {
//        this.itemClick = itemClick;
//    }
//
//    public void setLongClickListener(ItemClickInterface itemClick) {
//        this.itemClick = itemClick;
//    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        if (rowValues != null) {
            CheckStringIsNull(rowValues.get(position).getStrMainData(), holder.lblMainData);
//            holder.lblMainData.setText(rowValues.get(position).getStrMainData());
            CheckStringIsNull(rowValues.get(position).getStrDataDetails1(), holder.lblDetails1);

        }
    }

    public void CheckStringIsNull(String str, TextView textView){
        if (str != null) {
            if (str.isEmpty()) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(str);
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return rowValues.size();
    }

}

