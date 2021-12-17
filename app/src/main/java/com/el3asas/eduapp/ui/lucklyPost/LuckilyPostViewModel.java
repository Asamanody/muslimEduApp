package com.el3asas.eduapp.ui.lucklyPost;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azan.AzanTimes;
import com.azan.Time;
import com.el3asas.eduapp.ui.db.Aentity;
import com.el3asas.eduapp.ui.db.AzkarEntity;
import com.el3asas.eduapp.ui.db.Hentity;
import com.el3asas.eduapp.ui.db.LiveDataListener;
import com.el3asas.eduapp.ui.db.Qentity;
import com.el3asas.eduapp.ui.db.Repository;
import com.el3asas.eduapp.ui.models.Entity;
import com.el3asas.eduapp.ui.prayer.PrayItem;
import com.el3asas.eduapp.ui.prayer.PrayProperties;
import com.el3asas.eduapp.ui.prayer.SallahAndDiff;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LuckilyPostViewModel extends ViewModel implements Listener {
    private static final String TAG = "tttttt luckly";
    private static SharedPreferences lucklyPref;
    private static Repository repository;
    private String currentDate;
    private LifecycleOwner lifecycleOwner;
    public MutableLiveData<Entity> model1;
    public MutableLiveData<Entity> model2;
    public MutableLiveData<Entity> model3;
    private static CompositeDisposable disposable;
    private SharedPreferences.Editor edit;
    private static final List<PrayItem> prayItems = new ArrayList<>();
    private Disposable insQ;
    private Disposable insH;
    private Disposable insA;
    private static Disposable updateQ, updateH, updateA;

    public void init(SharedPreferences sharedPreferences, LifecycleOwner lifecycleOwner2) {
        disposable = new CompositeDisposable();
        this.lifecycleOwner = lifecycleOwner2;
        lucklyPref = sharedPreferences;
        edit = lucklyPref.edit();
        this.currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(new Date());
        repository = Repository.getInstance();
        repository.init();
        // getAzkar();
        model1 = new MutableLiveData<>();
        model2 = new MutableLiveData<>();
        model3 = new MutableLiveData<>();
        if (lucklyPref.getString("date", "").equals(this.currentDate)) {
            offlineGetter();
            return;
        }

        int i2 = lucklyPref.getInt("num", 0) + 1;
        onlineGetter(i2, 1);
        insert();
    }

    private void getAzkar() {
        repository.getAllAzkar().observeOn(Schedulers.io()).subscribeOn(Schedulers.computation())
                .subscribe(azkarEntities -> Log.d(TAG, "getAzkar: -------------" + azkarEntities.get(0).getZekr()), throwable -> Log.d(TAG, "getAzkar: ---------" + throwable.getMessage()));
    }

    private void onlineGetter(int i2, int i3) {
        failedListener();

        Disposable doq = repository.getOnlyEntityOnliner("Quraan", "num", i2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o ->
                        {
                            model1.setValue(o);
                            edit.putInt("num", i2);
                            edit.putString("date", this.currentDate);
                            edit.putInt("nOMIDay", i3);
                            edit.apply();
                        }, e -> Log.d(TAG, "onlineGettertttttttt: " + e)
                        , () -> Log.d(TAG, "onlineGetter: success"));
        Disposable doh = repository.getOnlyEntityOnliner("hades", "num", i2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o ->
                        model2.setValue(o), e -> Log.d(TAG, "onlineGettertttttttt: " + e));
        Disposable doa = repository.getOnlyEntityOnliner("aqtbas", "num", i2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> model3.setValue(o), e -> Log.d(TAG, "onlineGettertttttttt: " + e));

        disposable.add(doa);
        disposable.add(doh);
        disposable.add(doq);
    }


    private void failedListener() {
        LiveDataListener.setListener(this);
    }

    private void offlineGetter() {
        Log.d(TAG, "offlineGetter: ++++++++++++++");
        int i = lucklyPref.getInt("num", 0);
        findQ(i);
        findH(i);
        findA(i);
    }

    public void getNew() {
        int i = lucklyPref.getInt("nOMIDay", 0) + 1;
        if (i < 3) {
            int i2 = lucklyPref.getInt("num", 0) + 1;
            onlineGetter(i2, i);
            insert();
        }
    }

    public boolean getFreeQutes() {
        int i = lucklyPref.getInt("nOMIDay", 0) + 1;
        if (i < 3)
            return true;
        Toast.makeText(LucklyFragment.binding.getRoot().getContext(), "لقد تخطيت الحد الاقصى من اليوميات يمكن مراجعه السابق الان", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void insert() {
        model1.observe(lifecycleOwner, entity -> {
            Qentity q = new Qentity(entity.getQuot(), entity.getComment(), entity.getInfo(), entity.getNum(), 0);
            insQ = repository.insertQr(q)
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> Log.d(TAG, "insert: success"), e -> Log.d(TAG, "insert: failed"));
            disposable.add(insQ);
        });
        model2.observe(lifecycleOwner, entity -> {
            Hentity q = new Hentity(entity.getQuot(), entity.getComment(), entity.getInfo(), entity.getNum(), 0);
            insH = repository.insertHr(q)
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> Log.d(TAG, "insert: success"), e -> Log.d(TAG, "insert: failed"));
            disposable.add(insH);
        });
        model3.observe(lifecycleOwner, entity -> {
            Aentity q = new Aentity(entity.getQuot(), entity.getComment(), entity.getInfo(), entity.getNum(), 0);

            insA = repository.insertAr(q)
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> Log.d(TAG, "insert: success"), e -> Log.d(TAG, "insert: failed"));
            disposable.add(insA);
        });

    }

    public void findQ(int i) {
        Disposable dfq = repository.findQr(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> model1.setValue(o), e -> Log.d(TAG, "findQ: " + e));
        disposable.add(dfq);
    }

    public void findH(int i) {
        Disposable dfh = repository.findHr(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> model2.setValue(o), e -> Log.d(TAG, "findQ: " + e));
        disposable.add(dfh);
    }

    public void findA(int i) {
        Disposable dfa = repository.findAr(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> model3.setValue(o), e -> Log.d(TAG, "findQ: " + e)
                );
        disposable.add(dfa);
    }

    public static void update(int i) {

        switch (LuklyAdapter.pos) {
            case 0:
                updateQ = repository.updateQ(lucklyPref.getInt("num", 0), i)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(() -> disposable.remove(updateQ), e -> Log.d(TAG, "updateQ: aaaaaaeeeeeee" + e));
                disposable.add(updateQ);
                break;
            case 1:
                updateH = repository.updateH(lucklyPref.getInt("num", 0), i)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(() -> disposable.remove(updateH), e -> Log.d(TAG, "updateH: aaaaaaeeeeeee" + e));
                disposable.add(updateH);
                break;
            case 2:
                updateA = repository.updateA(lucklyPref.getInt("num", 0), i)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(() -> disposable.remove(updateA), e -> Log.d(TAG, "updateA: aaaaaaeeeeeee" + e));
                disposable.add(updateA);
                break;
        }
    }

    public void initPray(AzanTimes p) {
        if (prayItems.size() == 0) {
            prayItems.add(new PrayItem("صلاه الفجر"
                    , getTimeCalender(p.fajr())));
            prayItems.add(new PrayItem("صلاه الشروق"
                    , getTimeCalender(p.shuruq())));
            prayItems.add(new PrayItem(
                    "صلاه الظهر"
                    , getTimeCalender(p.thuhr())));
            prayItems.add(new PrayItem("صلاه العصر"
                    , getTimeCalender(p.assr())));
            prayItems.add(new PrayItem("صلاه المغرب",
                    getTimeCalender(p.maghrib())));
            prayItems.add(new PrayItem("صلاه العشاء"
                    , getTimeCalender(p.ishaa())));
        }
    }

    private Calendar getTimeCalender(Time t) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, t.getHour());
        calendar.set(Calendar.MINUTE, t.getMinute());
        return calendar;
    }

    public List<PrayItem> getPrayItems() {
        return prayItems;
    }

    /**************************/

    protected SallahAndDiff getSallahLoc(AzanTimes azanTimes) throws ParseException {
        return PrayProperties.getInctance().getSallahLoc(azanTimes);
    }

    /*****************************/

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    @Override
    public void onChange(boolean b) {
        Handler handler = new Handler();
        Runnable runnable = this::offlineGetter;
        handler.postDelayed(runnable, 5000);
        if (!b) {
            handler.removeCallbacks(runnable);
        }
    }

    public void setAzkarToDb(List<AzkarEntity> entities) {
        disposable.add(repository.insertAzkars(entities).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(() -> Log.d(TAG, "setAzkarToDb: -----------ttttt")
                        , throwable -> Log.d(TAG, "setAzkarToDb: ------" + throwable.getMessage())));
    }
}