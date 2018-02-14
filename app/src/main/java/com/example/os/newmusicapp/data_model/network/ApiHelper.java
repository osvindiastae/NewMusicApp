package com.example.os.newmusicapp.data_model.network;

import com.example.os.newmusicapp.data_model.network.model.MusicDetails;
import com.example.os.newmusicapp.data_model.network.services.IRequestInterface;
import com.example.os.newmusicapp.data_model.network.services.ServiceConnection;

import io.reactivex.Observable;

/**
 * Created by Os on 13/02/2018.
 */

public class ApiHelper implements IApiHelper{

    // make an IRequest Object
    private IRequestInterface iRequestInterface;

    public ApiHelper() {
        this.iRequestInterface = ServiceConnection.getConnection();
    }

    @Override
    public Observable<MusicDetails> getRockDetails() {
        return this.iRequestInterface.getRockDetails();
    }
}
