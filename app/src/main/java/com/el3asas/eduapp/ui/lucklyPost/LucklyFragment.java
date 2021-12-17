package com.el3asas.eduapp.ui.lucklyPost;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.azan.Azan;
import com.azan.AzanTimes;
import com.azan.Method;
import com.azan.astrologicalCalc.Location;
import com.azan.astrologicalCalc.SimpleDate;
import com.el3asas.eduapp.R;
import com.el3asas.eduapp.databinding.FragmentLucklyBinding;
import com.el3asas.eduapp.ui.db.AzkarDB;
import com.el3asas.eduapp.ui.db.AzkarEntity;
import com.el3asas.eduapp.ui.db.DataBase;
import com.el3asas.eduapp.ui.models.Entity;
import com.el3asas.eduapp.ui.prayer.PrayProperties;
import com.el3asas.eduapp.ui.prayer.PrayerBGS;
import com.el3asas.eduapp.ui.prayer.SallahAndDiff;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class LucklyFragment extends Fragment implements View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    public static FragmentLucklyBinding binding;
    private static LuckilyPostViewModel luckilyPostViewModel;
    private Entity[] entities;
    private static final int LOCATION_REQUEST_CODE = 44;
    public static final MutableLiveData<Double> latitude = new MutableLiveData<>();
    public static final MutableLiveData<Double> longitude = new MutableLiveData<>();
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private SallahAndDiff diffTime;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private PrayTimes prayTimes;
    private boolean GpsOpend = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_luckly, container, false);
        DataBase.getInstance(requireActivity());
        AzkarDB.getInstance(requireActivity());

        BottomSheetBehavior<ConstraintLayout> b = BottomSheetBehavior.from(binding.ISheet.sheet);
        binding.ISheet.cursor.setOnClickListener(view -> {
            if (b.getState() != BottomSheetBehavior.STATE_EXPANDED)
                b.setState(BottomSheetBehavior.STATE_EXPANDED);
            else
                b.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        b.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.ISheet.cursor.setImageResource(R.drawable.row_down);
                } else {
                    binding.ISheet.cursor.setImageResource(R.drawable.row_up);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        luckilyPostViewModel = new ViewModelProvider(this).get(LuckilyPostViewModel.class);

        binding.setViewModel(luckilyPostViewModel);
        binding.setLifecycleOwner(this);
        SharedPreferences preferences = requireActivity().getSharedPreferences("Luckly", 0);

        luckilyPostViewModel.init(preferences, this);
        binding.ISheet.add.setOnClickListener(this);
        binding.ISheet.showPrev.setOnClickListener(this);
        binding.ISheet.azkarCard.setOnClickListener(this);

        setViewPager();
        binding.ISheet.progress.setProgress(100);

        latitude.observe(requireActivity(), aDouble -> longitude.observe(requireActivity(), aDouble1 -> {
            SimpleDate today = new SimpleDate(new GregorianCalendar());
            Location loc = new Location(aDouble, aDouble1, 2.0, 0);
            Azan azan = new Azan(loc, Method.Companion.getEGYPT_SURVEY());
            AzanTimes prayerTimes = azan.getPrayerTimes(today);
            try {
                getNextSallah(prayerTimes);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            binding.ISheet.prayerTime.setOnClickListener(v -> {
                if (prayTimes == null) {
                    luckilyPostViewModel.initPray(prayerTimes);
                    prayTimes = new PrayTimes(preferences, luckilyPostViewModel.getPrayItems());
                }
                if (!prayTimes.isShow()) {
                    prayTimes.show(requireActivity().getSupportFragmentManager(), "00");
                    //NavHostFragment.findNavController(this).navigate(R.id.action_lucklyFragment_to_prayTimes);
                }
            });

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("locationSaved", true);
            editor.putString("latitude", aDouble + "");
            editor.putString("longitude", aDouble1 + "");
            editor.apply();

            checkFirstOpen(preferences);
            setProgressTime();
        }));
        if (preferences.getBoolean("locationSaved", false)) {
            longitude.setValue(Double.parseDouble(preferences.getString("longitude", "")));
            latitude.setValue(Double.parseDouble(preferences.getString("latitude", "")));
        } else {
            getLocation();
        }
        return binding.getRoot();
    }

    private void checkFirstOpen(SharedPreferences preferences) {
        if (preferences.getBoolean("firstOpen", true) || !PrayerBGS.playing_flag) {
            Intent intent = new Intent(requireActivity(), PrayerBGS.class);
            intent.putExtra("firstOpen", true);
            ContextCompat.startForegroundService(requireActivity(), intent);
            PrayProperties.getInctance().setPrayProperaties(requireActivity(), preferences);
            preferences.edit().putBoolean("firstOpen", false).apply();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ISheet.add)
            getNextQuots();
        else if (v == binding.ISheet.showPrev)
            showPrevPage();
        else if (v == binding.ISheet.azkarCard)
            try {
                NavHostFragment.findNavController(this).navigate(R.id.action_lucklyFragment_to_mainAzkarrFragment);
            } catch (IllegalArgumentException ignored) {
            }
    }

    private void setViewPager() {
        entities = new Entity[3];
        LuklyAdapter luklyAdapter = new LuklyAdapter(requireActivity());

        luckilyPostViewModel.model1.observe(requireActivity(),
                entity -> {
                    entities[0] = entity;
                    luckilyPostViewModel.model2.observe(requireActivity(), entity1 -> {
                        entities[1] = entity1;
                        luckilyPostViewModel.model3.observe(requireActivity(), entity2 -> {
                            entities[2] = entity2;
                            luklyAdapter.setList(entities);
                            binding.shimmerLayout.setVisibility(View.GONE);
                            binding.viewPager22.setVisibility(View.VISIBLE);
                        });
                    });
                });
        binding.viewPager22.setAdapter(luklyAdapter);
        binding.viewPager22.setClipToPadding(false);
        binding.viewPager22.setClipChildren(false);
        binding.viewPager22.setOffscreenPageLimit(3);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float t = 1 - Math.abs(position);
            page.setScaleY(.95f + t * .05f);
        });
        binding.viewPager22.setPageTransformer(transformer);
        binding.viewPager22.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LuklyAdapter.pos = position;
            }
        });
    }

    private void getNextSallah(AzanTimes azanTimes) throws ParseException {
        String t = "";
        diffTime = luckilyPostViewModel.getSallahLoc(azanTimes);
        switch (diffTime.getI()) {
            case 1:
                t = "يتبقى على صلاه الشروق";
                break;
            case 2:
                t = "يتبقى على صلاه الظهر";
                break;
            case 3:
                t = "يتبقى على صلاه العصر";
                break;
            case 4:
                t = "يتبقى على صلاه المغرب";
                break;
            case 5:
                t = "يتبقى على صلاه العشاء";
                break;
            case 0:
                t = "يتبقى على صلاه الفجر";
                break;
        }
        binding.title.setText(t);

        binding.time.setText(diffB2TimesToStr(diffTime.getDiff() - diffTime.getLetfTime()));
        binding.circularProgressBar.setProgress(getLeftTime(diffTime.getDiff(), diffTime.getLetfTime()));
    }

    private void setProgressTime() {
        disposable.add(Observable.interval(1, 1, TimeUnit.MINUTES)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        call();
                    }

                    public void call() {
                        diffTime.setLetfTime(diffTime.getLetfTime() + 60000);
                        binding.time.setText(diffB2TimesToStr(diffTime.getDiff() - diffTime.getLetfTime()));
                    }
                }, e -> Log.d(TAG, "setProgressTime: error")));
    }

    public String diffB2TimesToStr(long difference) {
        int hours = (int) (difference / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * hours)) / (1000 * 60);
        return String.format(Locale.ENGLISH, "%02d:%02d", hours > 0 ? hours : -hours, min > 0 ? min : -min);
    }

    private float getLeftTime(long all, long left) {
        return ((float) left / all) * 100;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
        if (LocationService.isRunning) {
            Intent locationService = new Intent(requireActivity(), LocationService.class);
            requireActivity().stopService(locationService);
        }
    }

    public void getNextQuots() {
        final ValueAnimator anim = ValueAnimator.ofFloat(1f, .8f);
        anim.setDuration(200);
        anim.addUpdateListener(animation -> {
            binding.ISheet.add.setScaleX((Float) animation.getAnimatedValue());
            binding.ISheet.add.setScaleY((Float) animation.getAnimatedValue());
        });
        anim.setStartDelay(0);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
        if (luckilyPostViewModel.getFreeQutes()) {
            binding.viewPager22.setVisibility(View.GONE);
            binding.shimmerLayout.setVisibility(View.VISIBLE);
            luckilyPostViewModel.getNew();
            binding.ISheet.progress.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(binding.ISheet.progress, "progress", 0, 100);
            animation.setDuration(3000);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else {
            ValueAnimator anim2 = ValueAnimator.ofInt(0, 45);
            anim2.setDuration(300);
            anim2.addUpdateListener(animation -> binding.ISheet.addImg.setRotation((int) animation.getAnimatedValue()));
            anim2.setStartDelay(0);
            anim2.setRepeatCount(1);
            anim2.setRepeatMode(ValueAnimator.REVERSE);
            anim2.start();
        }
    }

    public void showPrevPage() {
        NavHostFragment.findNavController(this).navigate(R.id.action_lucklyFragment_to_previousPostFragment);
    }

    @SuppressLint("MissingPermission")
    private void showLocation() {
        if (isGpsOn()) {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Log.d(TAG, "showLocation: ++++++++++++");
                            latitude.setValue(location.getLatitude());
                            longitude.setValue(location.getLongitude());
                            GpsOpend = false;
                        } else {
                            Log.d(TAG, "showLocation: ---------------");
                            showLocation();
                        }
                    });
        } else {
            showGpsAlertDialog();
        }
    }

    private void showGpsAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireActivity());
        alertDialogBuilder
                .setMessage("فتح ال GPS لتحديث بيانات صلواتك ؟")
                .setCancelable(false)
                .setPositiveButton("فتح",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                            GpsOpend = true;
                            dialog.dismiss();
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private boolean isGpsOn() {
        final LocationManager manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            getLocation();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getLocation() {
        if (Build.VERSION.SDK_INT <= 28) {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                requestPermissions(permissions, 44);
            } else
                showLocation();
        } else {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                requestPermissions(permissions, 44);
            } else {
                Intent locationService = new Intent(requireActivity(), LocationService.class);
                ContextCompat.startForegroundService(requireActivity(), locationService);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GpsOpend) {
            showLocation();
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    private void getAzkar() throws FileNotFoundException {
        String j = loadJSONFromAsset();
        Gson gson = new Gson();
        Type founderListType = new TypeToken<ArrayList<AzkarEntity>>() {
        }.getType();
        List<AzkarEntity> azkarEntity = gson.fromJson(j, founderListType);
        //Data data=gson.fromJson(j,Data.class);
        luckilyPostViewModel.setAzkarToDb(azkarEntity);
        Log.d(TAG, "getAzkar: -----------------" + azkarEntity.get(0).getZekr());
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = requireActivity().getAssets().open("databases/azkar.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}