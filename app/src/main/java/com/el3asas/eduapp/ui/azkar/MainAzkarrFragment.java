package com.el3asas.eduapp.ui.azkar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.FragmentMainAzkarrListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAzkarrFragment extends BottomSheetDialogFragment implements MainAzkarAdapter.CardListener {
    private FragmentMainAzkarrListBinding binding;
    private List<MainAzkarItem> azkarItems;
    private MainAzkarrFragmentDirections.ActionMainAzkarrFragmentToListedAzkarFragment action;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainAzkarrListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        azkarItems = new ArrayList<>();
        azkarItems.add(new MainAzkarItem(R.drawable.ic_shrouq, "اذكار الصباح"));
        azkarItems.add(new MainAzkarItem(R.drawable.ic_maghrib, "اذكار المساء"));
        azkarItems.add(new MainAzkarItem(R.drawable.ic_pray, "اذكار الصلاه"));
        azkarItems.add(new MainAzkarItem(R.drawable.daily_ic, "اذكار يوميه"));
        MainAzkarAdapter adapter = new MainAzkarAdapter(this, azkarItems);
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void CardClickListener(int position) {
        switch (position) {
            case 0:
                action = MainAzkarrFragmentDirections.actionMainAzkarrFragmentToListedAzkarFragment(new String[]{"أذكار الصباح"});
                action.setCategoryTitle(azkarItems.get(position).getAzkarName());
                NavHostFragment.findNavController(this).navigate(action);
                break;
            case 1:
                action = MainAzkarrFragmentDirections.actionMainAzkarrFragmentToListedAzkarFragment(new String[]{"أذكار المساء"});
                action.setCategoryTitle(azkarItems.get(position).getAzkarName());
                NavHostFragment.findNavController(this).navigate(action);
                break;
            case 2:
                action = MainAzkarrFragmentDirections.actionMainAzkarrFragmentToListedAzkarFragment(new String[]{"أذكار الآذان"});
                action.setCategoryTitle(azkarItems.get(position).getAzkarName());
                NavHostFragment.findNavController(this).navigate(action);
                break;
            case 3:
                action = MainAzkarrFragmentDirections.actionMainAzkarrFragmentToListedAzkarFragment(new String[]{"دعاء الاستفتاح"});
                action.setCategoryTitle(azkarItems.get(position).getAzkarName());
                NavHostFragment.findNavController(this).navigate(action);
                break;
        }
    }
}