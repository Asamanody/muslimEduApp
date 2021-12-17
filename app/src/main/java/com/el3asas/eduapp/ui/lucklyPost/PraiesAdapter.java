package com.el3asas.eduapp.ui.lucklyPost;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.PrayTimeItemBinding;
import com.el3asas.eduapp.ui.prayer.PrayItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PraiesAdapter extends RecyclerView.Adapter<PraiesAdapter.MyViewHolder> {
    private List<PrayItem> prayTimes;
    private SharedPreferences pref;
    private SimpleDateFormat s;

    private static prayClickListener listener;

    private final String[] prays = {"azanFajr","azanShuruq","azanZohr","azanAssr","azanMaghrib","azanIshaa"};

    public void setAdapter(List<PrayItem> list, SharedPreferences pref,prayClickListener listener) {
        this.prayTimes =new ArrayList<>(list);
        this.pref=pref;
        PraiesAdapter.listener =listener;
        s=new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    }

    public PraiesAdapter() {}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PrayTimeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.pray_time_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listItemBinding.name.setText(prayTimes.get(position).name);
        holder.listItemBinding.time.setText(setTextSallahTimes(prayTimes.get(position).calendar));
        switch (position){
            case 0:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[0], true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_fajr);
                break;
            case 1:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[1],true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_shrouq);
                break;
            case 2:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[2],true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_zohr);
                break;
            case 3:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[3],true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_assr);
                break;
            case 4:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[4],true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_maghrib);
                break;
            case 5:
                holder.listItemBinding.box.setChecked(pref.getBoolean(prays[5],true));
                holder.listItemBinding.img.setImageResource(R.drawable.ic_ishaa);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return prayTimes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        PrayTimeItemBinding listItemBinding;

        public MyViewHolder(@NonNull PrayTimeItemBinding itemView) {
            super(itemView.getRoot());
            listItemBinding = itemView;
            itemView.box.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            listener.clickListener(getBindingAdapterPosition(),isChecked);
        }
    }

    private String setTextSallahTimes(Calendar c){
        return s.format(c.getTime());
    }

    public interface prayClickListener{
        void clickListener(int pos, boolean checked);
    }
}