package com.el3asas.eduapp.ui.prviosPosts.all_previous.mian;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.QFragmentBinding;
import com.el3asas.eduapp.ui.models.Entity;

import java.util.List;

public class tabQ extends Fragment implements MyAdapterQ.clickListener {
    //private SharedViewModel mSharedModel;
    private static MyAdapterQ myAdapter;
    private List<Entity> entities;
    private QFragmentBinding aFragmentBinding;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAdapter=new MyAdapterQ(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.q_fragment, viewGroup, false);

        //mSharedModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        aFragmentBinding.items.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        PageViewModel.qData.observe(getViewLifecycleOwner(), entities -> {
            this.entities=entities;
            myAdapter.setList(this.entities);
            myAdapter.notifyDataSetChanged();
            observeSharedData();
        });
        aFragmentBinding.items.setAdapter(myAdapter);
        return aFragmentBinding.getRoot();
    }

    private void observeSharedData() {
      /*  previousPostFragment.sharedViewModel.searchQueryLiveData.observe(getViewLifecycleOwner(), s -> {myAdapter.getFilter().filter(s);
            Log.d("", "observeSharedData: qqqqqqqq  " + s);
        });*/
    }

    @Override
    public void onClickItemListerer(int position) {
        Log.d("TAGggggggggg", "onClickItemListerer: "+position+"------------"+entities.get(position).getQuot());
    }
}
