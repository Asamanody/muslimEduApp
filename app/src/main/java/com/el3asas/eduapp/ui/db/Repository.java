package com.el3asas.eduapp.ui.db;

import android.util.Log;

import com.el3asas.eduapp.ui.models.Entity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {
    private QDao qDao;
    private HDao hDao;
    private ADao aDao;
    private AzkarDao azkarDao;

    private static Repository instance;

    Repository() {
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public void init() {
        DataBase dataBase = DataBase.getInstance();
        AzkarDB azkarDB=AzkarDB.getInstance();
        qDao = dataBase.qDao();
        aDao = dataBase.aDao();
        hDao = dataBase.hDao();
        azkarDao = azkarDB.azkarDao();
    }

    /// Quote
    public Observable<List<Entity>> getAllQr() {
        return qDao.getAllQr();
    }

    public Completable insertQr(Qentity note) {
        return qDao.insertQr(note);
    }

    public Single<Entity> findQr(int i) {
        return qDao.findQr(i);
    }

    public Completable updateQ(int num, int s) {
        return qDao.updateQStatusr(num, s);
    }

    /// Hades
    public Observable<List<Entity>> getAllHr() {
        return hDao.getAllHr();
    }

    public Completable insertHr(Hentity note) {
        return hDao.insertHr(note);
    }

    public Single<Entity> findHr(int i) {
        return hDao.findHr(i);
    }

    public Completable updateH(int num, int s) {
        return hDao.updateHStatusr(num, s);
    }

    /// Aqtbas
    public Observable<List<Entity>> getAllAr() {
        return aDao.getAllAr();
    }

    public Completable insertAr(Aentity note) {
        return aDao.insertQr(note);
    }

    public Single<Entity> findAr(int i) {
        return aDao.findAr(i);
    }

    public Completable updateA(int num, int s) {
        return aDao.updateAStatusr(num, s);
    }

    public Observable<Entity> getOnlyEntityOnliner(String collectionPath, String FieldName, int val) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(collectionPath);
        Query query = collectionReference.whereEqualTo(FieldName, val);
        LiveDataListener listener = new LiveDataListener();
        return listener.getEntity(query);
    }

    public Observable getAllPrays() {
        PrayDao prayDao = DataBase.getInstance().prayDao();
        return prayDao.getAllPrays();
    }

    public Completable updatePray(PrayEntity prayEntity) {
        PrayDao prayDao = DataBase.getInstance().prayDao();
        return prayDao.updatePray(prayEntity);
    }

    public Completable insertPray(PrayEntity prayEntity) {
        PrayDao prayDao = DataBase.getInstance().prayDao();
        return prayDao.insertPray(prayEntity);
    }

    public Observable<List<AzkarEntity>> getAllAzkar() {
        return azkarDao.getAllAzkar();
    }

    public Observable<List<AzkarEntity>> getAzkarByCat(String cat) {
        Log.d("TAG", "getAzkarByCat: ---------------------------");
        return azkarDao.getAzkarByCat(cat);
    }

    public Completable insertAzkars(List<AzkarEntity> entities){
        return azkarDao.insertAzkar(entities);
    }
}
