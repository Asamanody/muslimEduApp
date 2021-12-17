package com.el3asas.eduapp.ui.lucklyPost;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.PrayTimeFragmentBinding;
import com.el3asas.eduapp.ui.prayer.PrayItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class PrayTimes extends BottomSheetDialogFragment implements PraiesAdapter.prayClickListener {
    private final SharedPreferences pref;
    private final SharedPreferences.Editor edit;
    private final List<PrayItem> prayItems;
    private final String[] prays = {"azanFajr", "azanShuruq", "azanZohr", "azanAssr", "azanMaghrib", "azanIshaa"};
    private boolean isShow = false;

    PrayTimes(SharedPreferences p, List<PrayItem> list) {
        pref = p;
        edit = pref.edit();
        prayItems = list;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PrayTimeFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pray_time_fragment, container, false);
        PraiesAdapter adapter = new PraiesAdapter();
        adapter.setAdapter(prayItems, pref, this);
        binding.praiesTimes.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.praiesTimes.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(16, 16, 16, 16);
            }
        });
        binding.praiesTimes.setAdapter(adapter);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dia -> {
            BottomSheetDialog dialog = (BottomSheetDialog) dia;
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            assert bottomSheet != null;
            bottomSheet.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
            BottomSheetBehavior.from(bottomSheet).setHideable(true);
        });
        bottomSheetDialog.setCancelable(true);
        return bottomSheetDialog;
    }

    @Override
    public void clickListener(int pos, boolean isChecked) {
        if (isChecked) {
            edit.putBoolean(prays[pos], true);
            edit.apply();
        } else {
            edit.putBoolean(prays[pos], false);
            edit.apply();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isShow = true;
        Log.d("", "onAttach: --------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isShow = false;
        Log.d("", "onDetach: ---------");
    }

    public boolean isShow() {
        return isShow;
    }
}