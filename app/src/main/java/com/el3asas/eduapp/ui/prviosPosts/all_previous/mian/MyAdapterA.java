package com.el3asas.eduapp.ui.prviosPosts.all_previous.mian;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.ListItemBinding;
import com.el3asas.eduapp.ui.models.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MyAdapterA extends RecyclerView.Adapter<MyAdapterA.MyViewHolder> implements Filterable {
    private List<Entity> Entity;
    private List<Entity> EntityFull;
    private static clickListener listener;

    public MyAdapterA(List<Entity> list) {
        Entity = list;
        this.EntityFull = new ArrayList<>(list);
    }

    public MyAdapterA(clickListener l) {
        listener=l;
    }

    public void setList(List<Entity> list){
        Entity = list;
        this.EntityFull = new ArrayList<>(list);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listItemBinding.setModel(Entity.get(position));
    }

    @Override
    public int getItemCount() {
        return Entity!=null?Entity.size():0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ListItemBinding listItemBinding;

        public MyViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());
            listItemBinding = itemView;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickItemListerer(getBindingAdapterPosition());
        }
    }

    public Filter getFilter() {
        return getInstance();
    }

    private Filter getInstance() {
        return new Filter() {
            public Filter.FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Entity> arrayList = new ArrayList<>();
                if (charSequence == null || charSequence.length() == 0) {
                    arrayList.addAll(MyAdapterA.this.EntityFull);
                } else {
                    String trim = charSequence.toString().toLowerCase().trim();
                    for (Entity entity : MyAdapterA.this.EntityFull) {
                        if (Pattern.compile("[\\p{P}\\p{Mn}]").matcher(entity.getQuot().toLowerCase()).reset().replaceAll("").replace("أ", "ا").replace("إ", "ا").replace("آ", "ا").contains(trim)) {
                            arrayList.add(entity);
                        }
                    }
                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                Entity.clear();
                if(filterResults.count!=0)
                Entity.addAll((List)filterResults.values);
                MyAdapterA.this.notifyDataSetChanged();
            }
        };
    }

    public interface clickListener{
        void onClickItemListerer(int position);
    }
}