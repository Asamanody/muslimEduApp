package com.el3asas.eduapp.ui.azkar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;

import java.util.List;

/*** Created by el3sas on 11/24/2021 ,
 */
public class MainAzkarAdapter extends RecyclerView.Adapter<MainAzkarAdapter.MainAzkarAdapterViewHolder> {

    private List<MainAzkarItem> items;
    private static CardListener cardListener;

    public MainAzkarAdapter(CardListener cardListener, List<MainAzkarItem> items) {
        this.items = items;
        MainAzkarAdapter.cardListener=cardListener;
    }

    @NonNull
    @Override
    public MainAzkarAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_azkar_item, parent, false);
        return new MainAzkarAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainAzkarAdapterViewHolder holder, int position) {
        MainAzkarItem item = items.get(position);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    static class MainAzkarAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView img;

        MainAzkarAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
        }

        void set(MainAzkarItem item) {
            //UI setting code
            textView.setText(item.getAzkarName());
            img.setImageResource(item.getImgRes());
        }

        @Override
        public void onClick(View view) {
            cardListener.CardClickListener(getBindingAdapterPosition());
        }
    }

    public interface CardListener {
        void CardClickListener(int position);
    }
}