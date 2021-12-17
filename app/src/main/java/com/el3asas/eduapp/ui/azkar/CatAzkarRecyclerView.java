package com.el3asas.eduapp.ui.azkar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.ZekrItemBinding;
import com.el3asas.eduapp.ui.db.AzkarEntity;

public class CatAzkarRecyclerView extends RecyclerView.Adapter<CatAzkarRecyclerView.CatAzkarRecyViewHolder> {
    private AzkarEntity[] list;

    @NonNull
    @Override
    public CatAzkarRecyclerView.CatAzkarRecyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ZekrItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.zekr_item, parent, false);
        return new CatAzkarRecyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatAzkarRecyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.zerItemBinding.setViewModel(list[position]);
    }

    @Override
    public int getItemCount() {

        return list != null ? list.length : 0;
    }

    static class CatAzkarRecyViewHolder extends RecyclerView.ViewHolder {
        ZekrItemBinding zerItemBinding;

        public CatAzkarRecyViewHolder(@NonNull ZekrItemBinding itemView) {
            super(itemView.getRoot());
            zerItemBinding = itemView;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(AzkarEntity[] l) {
        list = l;
        notifyDataSetChanged();
    }

}