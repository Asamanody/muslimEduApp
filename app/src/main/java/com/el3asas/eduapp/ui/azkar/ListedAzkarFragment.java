package com.el3asas.eduapp.ui.azkar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.el3asas.eduapp.databinding.FragmentListedAzkarListBinding;
import com.el3asas.eduapp.ui.db.AzkarEntity;

import java.util.List;

public class ListedAzkarFragment extends Fragment implements MyListedAzkarRecyclerViewAdapter.ClickListener {
    private List<AzkarEntity> azkarEntities;
    private ListedFragmentViewModel viewModel;
    private String[] category;
    private FragmentListedAzkarListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListedAzkarListBinding.inflate(inflater);
        String title = ListedAzkarFragmentArgs.fromBundle(getArguments()).getCategoryTitle();
        category = ListedAzkarFragmentArgs.fromBundle(getArguments()).getCategoriesName();
        binding.title.setTitle(title);
        binding.title.setNavigationOnClickListener(view -> NavHostFragment.findNavController(this).navigateUp());

        viewModel = new ViewModelProvider(this).get(ListedFragmentViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MyListedAzkarRecyclerViewAdapter adapter = new MyListedAzkarRecyclerViewAdapter(this);
        viewModel.getAllAzkar(category).observe(this, azkarEntities1 -> {
            adapter.setList(azkarEntities1);
            azkarEntities = azkarEntities1;
        });
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onClickListener(int pos) {
        ListedAzkarFragmentDirections.ActionListedAzkarFragmentToAzkarFragment directions = ListedAzkarFragmentDirections.actionListedAzkarFragmentToAzkarFragment((AzkarEntity[]) azkarEntities.toArray(new AzkarEntity[0]));
        directions.setPos(pos);
        NavHostFragment.findNavController(this).navigate(directions);
    }
}