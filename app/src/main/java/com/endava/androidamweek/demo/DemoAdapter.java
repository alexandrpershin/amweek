package com.endava.androidamweek.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private List<DemoItem> demoList;


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meetingTitle)
        TextView meetingTitle;

        @BindView(R.id.foldingCell)
        FoldingCell foldingCell;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foldingCell.toggle(false);
                }
            });
        }

    }

    public DemoAdapter(List<DemoItem> demoList) {
        this.demoList = demoList;
    }

    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoItem item = demoList.get(position);
        holder.meetingTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return demoList.size();
    }

}