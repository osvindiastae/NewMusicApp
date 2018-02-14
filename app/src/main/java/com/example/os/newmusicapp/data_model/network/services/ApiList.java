package com.example.os.newmusicapp.data_model.network.services;

/**
 * Created by Os on 10/02/2018.
 */

/**
 * ApiList stores all the constants that are used to get data from the API and process it
 */
public class ApiList {
    public static final String BASE_URL = "https://itunes.apple.com/";

//    public static final String ROCK_API = "search?term=rock&amp;media=music&amp;entity=song&amp;limit=50";
    public static final String ROCK_API = "search?term=rock&media=music&entity=song&limit=50";
//    public static final String CLASSIC_API = "search?term=classick&amp;media=music&amp;entity=song&amp;limit=50";
    public static final String CLASSIC_API = "search?term=classic&media=music&entity=song&limit=50";
//    public static final String POP_API = "search?term=pop&amp;media=music&amp;entity=song&amp;limit=50";
    public static final String POP_API = "search?term=pop&amp;media=music&amp;entity=song&amp;limit=50";

    public static final String CURRENCY_GBP = "£ ";
}