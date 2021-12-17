package com.el3asas.eduapp.ui.lucklyPost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.LuklyItemBinding;
import com.el3asas.eduapp.ui.models.Entity;

public class LuklyAdapter extends RecyclerView.Adapter<LuklyAdapter.luklyViewHolder> {
    private Entity[] list;
    public static int pos;
    private final Context context;
    LuklyAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public LuklyAdapter.luklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LuklyItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.lukly_item, parent, false);
        return new luklyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LuklyAdapter.luklyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.luklyItemBinding.setViewModel(list[position]);
        switch (position){
            case 0:
                holder.luklyItemBinding.imageViewQ.setImageBitmap(Image.decodeSampledBitmapFromResource(context.getResources(), R.drawable.quraan, 100, 100));
                break;
            case 1:holder.luklyItemBinding.imageViewQ.setImageBitmap(Image.decodeSampledBitmapFromResource(context.getResources(), R.drawable.hades, 100, 100));
                break;
            case 2:holder.luklyItemBinding.imageViewQ.setImageBitmap(Image.decodeSampledBitmapFromResource(context.getResources(), R.drawable.books, 100, 100));
                break;
        }
    }

    @Override
    public int getItemCount() {

        return list!=null?list.length:0;
    }

    static class luklyViewHolder extends RecyclerView.ViewHolder{
        LuklyItemBinding luklyItemBinding;
        public luklyViewHolder(@NonNull LuklyItemBinding itemView) {
            super(itemView.getRoot());
            luklyItemBinding=itemView;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(Entity[] l){
        list=l;
        notifyDataSetChanged();
    }

}