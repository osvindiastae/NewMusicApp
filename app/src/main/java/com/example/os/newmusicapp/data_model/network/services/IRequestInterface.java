package com.example.os.newmusicapp.data_model.network.services;

import com.example.os.newmusicapp.data_model.network.model.MusicDetails;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Os on 10/02/2018.
 */

/**
 * Interface used to handle the API requests
 */
public interface IRequestInterface {
    @GET(ApiList.ROCK_API)
    Observable<MusicDetails> getRockDetails();

    @GET(ApiList.CLASSIC_API)
    Observable<MusicDetails> getClassicDetails();

    @GET(ApiList.POP_API)
    Observable<MusicDetails> getPopDetails();
}
