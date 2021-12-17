package com.el3asas.eduapp.ui.prviosPosts.all_previous;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.FragmentPrviousPostBinding;
import com.el3asas.eduapp.ui.db.DataBase;
import com.el3asas.eduapp.ui.prviosPosts.all_previous.mian.PageViewModel;
import com.el3asas.eduapp.ui.prviosPosts.all_previous.mian.SectionsPagerAdapter;
import com.el3asas.eduapp.ui.prviosPosts.all_previous.mian.tabA;
import com.el3asas.eduapp.ui.prviosPosts.all_previous.mian.tabH;
import com.el3asas.eduapp.ui.prviosPosts.all_previous.mian.tabQ;

public class previousPostFragment extends AppCompatActivity implements SearchView.OnQueryTextListener {
    @SuppressLint("StaticFieldLeak")
    private FragmentPrviousPostBinding binding;
    private int indicatorWidth;
    //public SharedViewModel sharedViewModel;
    private final tabQ tabq = new tabQ();
    private final tabA taba = new tabA();
    private final tabH tabh = new tabH();
    @SuppressLint("StaticFieldLeak")
    private static SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DataBase.getInstance(this);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_prvious_post);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        binding.setLifecycleOwner(this);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setSubmitButtonEnabled(false);

        PageViewModel.getAllQ();
        PageViewModel.getAllH();
        PageViewModel.getAllA();

        sectionsPagerAdapter.addFragment(tabq);
        sectionsPagerAdapter.addFragment(tabh);
        sectionsPagerAdapter.addFragment(taba);
        binding.viewPager22.setAdapter(sectionsPagerAdapter);
        binding.tab.setupWithViewPager(binding.viewPager22);
        binding.tab.post(() -> {
            indicatorWidth = binding.tab.getWidth() / binding.tab.getTabCount();
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            binding.indicator.setLayoutParams(indicatorParams);
        });
        binding.viewPager22.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
                float translationOffset = (positionOffset + i) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                binding.indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String str) {
        Log.d("", "onQueryTextChange: +++++++++" + str);
        //sharedViewModel.setData(str);
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}