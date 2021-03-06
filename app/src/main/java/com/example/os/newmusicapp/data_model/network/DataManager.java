package com.example.os.newmusicapp.data_model.network;

import com.example.os.newmusicapp.data_model.network.model.MusicDetails;

import io.reactivex.Observable;

/**
 * Created by Os on 13/02/2018.
 */

public class DataManager implements IDataManager{

    private IApiHelper iApiHelper;

    public DataManager() {
        this.iApiHelper = new ApiHelper();
    }

    @Override
    public Observable<MusicDetails> getRockDetails() {
        return iApiHelper.getRockDetails();
    }
}
