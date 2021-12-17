package com.el3asas.eduapp.ui.azkar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.databinding.FragmentListedAzkarBinding;
import com.el3asas.eduapp.ui.db.AzkarEntity;

import java.util.List;

public class MyListedAzkarRecyclerViewAdapter extends RecyclerView.Adapter<MyListedAzkarRecyclerViewAdapter.ViewHolder> {

    private List<AzkarEntity> mValues;
    private static ClickListener clickListener;

    public MyListedAzkarRecyclerViewAdapter(ClickListener clickListener) {
        MyListedAzkarRecyclerViewAdapter.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<AzkarEntity> list) {
        mValues = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentListedAzkarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fragmentListedAzkarBinding.setViewModel(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        if (mValues != null && mValues.size() != 0)
            return mValues.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentListedAzkarBinding fragmentListedAzkarBinding;

        public ViewHolder(FragmentListedAzkarBinding binding) {
            super(binding.getRoot());
            fragmentListedAzkarBinding = binding;
            fragmentListedAzkarBinding.alll.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClickListener(getBindingAdapterPosition());
        }
    }

    public interface ClickListener {
        void onClickListener(int pos);
    }
}