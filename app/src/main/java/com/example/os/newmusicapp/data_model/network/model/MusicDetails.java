package com.example.os.newmusicapp.data_model.network.model;

/**
 * Created by Os on 10/02/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MusicDetails is POJO used to process the JSON data received from the API
 */
public class MusicDetails {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<MusicResults> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<MusicResults> getResults() {
        return results;
    }

    public void setResults(List<MusicResults> results) {
        this.results = results;
    }

}