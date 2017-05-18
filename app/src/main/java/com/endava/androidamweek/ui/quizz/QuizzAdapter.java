package com.endava.androidamweek.ui.quizz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.endava.androidamweek.R;
import com.endava.androidamweek.data.callbacks.QuizzCallback;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.ViewHolder> {

    private final QuizzCallback quizzCallback;
    private List<Quizz> quizzList;
    private Utils utils;
    private boolean isActive;

    public void updateList() {
        this.quizzList = utils.getQuizzes();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quizz_time)
        TextView quizzTime;

        @BindView(R.id.quizz_date)
        TextView quizzDate;

        @BindView(R.id.winner)
        TextView quizzWinner;

        @BindView(R.id.quizzStatus)
        ImageView quizzStatus;

        @BindView(R.id.quizz_title)
        TextView quizzTitle;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    QuizzAdapter(QuizzCallback quizzCallback) {
        this.quizzCallback = quizzCallback;
        utils = new Utils();
        quizzList = utils.getQuizzes();
    }

    @Override
    public QuizzAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quizz_title, parent, false);
        return new QuizzAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuizzAdapter.ViewHolder holder, int position) {

        final Quizz item = quizzList.get(position);

        holder.quizzTitle.setText(item.getTitle());
        holder.quizzWinner.setText(item.getWinner());
        holder.quizzTime.setText(item.getTime());
        holder.quizzDate.setText(item.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizzCallback.OnClick(item);
            }
        });

            isActive = item.getStatus();

        if (isActive) {
            holder.quizzStatus.setImageResource(R.drawable.ic_bell);

        } else {
            holder.quizzStatus.setImageResource(R.drawable.ic_no);
        }

    }



    @Override
    public int getItemCount() {
        return quizzList.size();
    }

}
