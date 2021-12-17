package com.el3asas.eduapp.ui.azkar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.el3asas.eduapp.databinding.FragmentAzkarBinding;
import com.el3asas.eduapp.ui.db.AzkarEntity;

public class AzkarFragment extends Fragment {

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAzkarBinding binding = FragmentAzkarBinding.inflate(inflater);

        AzkarEntity[] azkarEntities = AzkarFragmentArgs.fromBundle(getArguments()).getAzkarList();
        int pos = AzkarFragmentArgs.fromBundle(getArguments()).getPos();
        CatAzkarRecyclerView catAzkarRecyclerView = new CatAzkarRecyclerView();
        catAzkarRecyclerView.setList(azkarEntities);

        assert azkarEntities != null;
        binding.title.setTitle(azkarEntities[pos].getCategory());

        binding.title.setNavigationOnClickListener(view -> NavHostFragment.findNavController(this).navigateUp());

        binding.list.setAdapter(catAzkarRecyclerView);
        binding.list.setClipToPadding(false);
        binding.list.setClipChildren(false);
        binding.list.setOffscreenPageLimit(3);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float t = 1 - Math.abs(position);
            page.setScaleY(.95f + t * .05f);
        });
        binding.list.setPageTransformer(transformer);
        binding.list.setCurrentItem(pos);
        binding.list.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.title.setTitle(azkarEntities[position].getCategory());
            }
        });
        return binding.getRoot();
    }
}