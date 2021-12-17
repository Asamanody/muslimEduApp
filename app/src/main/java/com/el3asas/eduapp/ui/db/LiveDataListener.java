package com.el3asas.eduapp.ui.db;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.el3asas.eduapp.ui.lucklyPost.Listener;
import com.el3asas.eduapp.ui.lucklyPost.LucklyFragment;
import com.el3asas.eduapp.ui.models.Entity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LiveDataListener extends LiveData<DocumentSnapshot> {
    private static Listener list;

    public LiveDataListener() {
    }

    public static void setListener(Listener listener){
        list=listener;
    }

    public Observable<Entity> getEntity(Query query) {
        Observable<Entity> observable = Observable.create(emitter -> query.addSnapshotListener((value, error) -> {
            assert value != null;
            List<Entity> entityList = value.toObjects(Entity.class);
            if (entityList.size() != 0){
                emitter.onNext(entityList.get(0));
                list.onChange(false);
            }
            else {
                list.onChange(true);
                Toast.makeText(LucklyFragment.binding.getRoot().getContext(), "تأكد من اتصالك بالانترنت !", Toast.LENGTH_SHORT).show();
            }
        }));

        observable = observable.subscribeOn(Schedulers.io());
        observable = observable.observeOn(Schedulers.computation());
        return observable;
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
