package com.example.os.newmusicapp.music_details.rock_music;

import com.example.os.newmusicapp.data_model.network.IDataManager;
import com.example.os.newmusicapp.data_model.network.model.MusicDetails;
import com.example.os.newmusicapp.ui.base.BasePresenter;
import com.example.os.newmusicapp.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Os on 13/02/2018.
 */

public class RockPresenter<V extends IRockMvpView>
        extends BasePresenter<V>
        implements IRockMvpPresenter<V> {
    public RockPresenter(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadMusicDetailsList() {
        getCompositeDisposable().add(
                getDataManager().getRockDetails()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<MusicDetails>() {
                                       @Override
                                       public void accept(MusicDetails musicDetails) throws Exception {
                                           getMvpView().onFetchDataSuccess(musicDetails);
                                       }},
                                new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().onFetchDataError(throwable.getMessage());
                            }
                        })
        );
    }
}
