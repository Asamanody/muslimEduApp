package com.el3asas.eduapp.ui.prviosPosts.all_previous.mian;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.el3asas.eduapp.R;
import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final int[] TAB_TITLES = {R.string.ayat, R.string.hades, R.string.aqtbas};
    private final List<Fragment> fragmentList = new ArrayList();
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager, 1);
        this.mContext = context;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragmentList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.mContext.getResources().getString(TAB_TITLES[i]);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        this.fragmentList.add(fragment);
    }
}
