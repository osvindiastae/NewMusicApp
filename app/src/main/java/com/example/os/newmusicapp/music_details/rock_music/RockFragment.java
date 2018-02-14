package com.example.os.newmusicapp.music_details.rock_music;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.os.newmusicapp.MainActivity;
import com.example.os.newmusicapp.R;
import com.example.os.newmusicapp.data_model.network.DataManager;
import com.example.os.newmusicapp.music_details.adapter.MusicAdapter;
import com.example.os.newmusicapp.music_details.adapter.MusicRealmAdapter;
import com.example.os.newmusicapp.data_model.network.model.MusicDetails;
import com.example.os.newmusicapp.data_model.network.model.MusicResults;
import com.example.os.newmusicapp.ui.base.BaseFragment;
import com.example.os.newmusicapp.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RockFragment extends BaseFragment implements IRockMvpView {

    private IRockMvpPresenter<RockFragment> rockFragmentIMusicDetailsMvpPresenter;

//    private IRequestInterface requestInterface;
    private RecyclerView recyclerView;

    // Swipe to refresh is declared
    private SwipeRefreshLayout refreshLayoutRock;

    // RxJava to make sure the app doesn't crash if the user
    // closes the app before the request from the api is handles
//    private CompositeDisposable compositeDisposable;


    public RockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Retains the state when orientation is changed doesn't work if its in a back stack
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rockFragmentIMusicDetailsMvpPresenter = new RockPresenter<>(new DataManager(),
                new AppSchedulerProvider(), new CompositeDisposable());
        this.rockFragmentIMusicDetailsMvpPresenter.onAttach(this);

        // Initialised the declared variables
//        requestInterface = ServiceConnection.getConnection();

        recyclerView = (RecyclerView) view.findViewById(R.id.rvRockContainer);
        // set the layout for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // composite disposable
//        compositeDisposable = new CompositeDisposable();

        // Call service
        callService();

        // setting the refresh function
        refreshLayoutRock = view.findViewById(R.id.swipeRefreshRock);
        refreshLayoutRock.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // clear the composite disposable
//        if ((compositeDisposable != null) && (!compositeDisposable.isDisposed())){
//            compositeDisposable.clear();
//        }
    }

    /**
     * displayRockResults gathers the data from the API and displays in recycler view
     * if there are any errors in retrieving data it throws an error and prints in the Log
     * if data is received calls the deleteRealmData() from the MainActivity to delete the data from
     * the Realm Database and then calls the processMusicResults() to add new data to the Realm Database
     * swipe to refresh is turned off after the date is refreshed and also if data cannot be reached
     */
//    private void displayRockResults(){
//        compositeDisposable.add(
//                requestInterface.getRockDetails()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<MusicDetails>() {
//                                       @Override
//                                       public void accept(MusicDetails musicDetails) throws Exception {
//                                           // Received information is passed to the MusicAdapter to process and display
//                                           recyclerView.setAdapter(new MusicAdapter(getActivity(), this, musicDetails.getResults(), R.layout.row_recycler));
//                                           // stop refresh after data displayed
//                                           refreshLayoutRock.setRefreshing(false);
//                                           // add data into realm database
//                                           MainActivity.deleteRealmData();
//                                           processMusicResults(musicDetails.getResults());
//                                       }
//                                   },
//                                new Consumer<Throwable>() {
//                                    @Override
//                                    public void accept(Throwable throwable) throws Exception {
//                                        // Error Code
//                                        Log.e("consumer__Info",throwable.getMessage());
//                                        // stop refresh is there is an error in receiving the data
//                                        refreshLayoutRock.setRefreshing(false);
//                                    }
//                                })
//        );
//    }

    /**
     * callService method listens to the Internet Connection and Toasts when there is no network
     * if Internet is available it calls the @displayRockResults
     * if Internet is not available it displays the data from the Realm Database if data is available
     * swipe to refresh is turned off if data cannot be reached
     */
    private void callService(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet){
                        if (isConnectedToInternet){
                            // display data from API
//                            displayRockResults();
                            rockFragmentIMusicDetailsMvpPresenter.loadMusicDetailsList();
                        } else {
                            // check Realm database for data
                            recyclerView.setAdapter(new MusicRealmAdapter(getActivity(), MainActivity.getRealmData(), R.layout.row_recycler));
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("You don't have internet connection")
                                    .setTitle("Network Alert!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            //Toast.makeText(getActivity().getApplicationContext(), "NO NETWORK!", Toast.LENGTH_LONG).show();
                            // stop refreshing is there is no network
                            refreshLayoutRock.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * processMusicResults as the name suggests the method processes the list of music result received
     * from the API and adds the data to the Realm Database
     * @param musicResults
     */
    private void processMusicResults(List<MusicResults> musicResults){
        for (MusicResults currentRecord : musicResults){
            MainActivity.saveToRealm(
                    currentRecord.getArtistName(),
                    currentRecord.getCollectionName(),
                    currentRecord.getArtworkUrl60(),
                    currentRecord.getTrackPrice().toString()
            );
        }
    }

    @Override
    public void onFetchDataProgress() {

    }

    @Override
    public void onFetchDataSuccess(MusicDetails musicDetails) {
        recyclerView.setAdapter(new MusicAdapter(getActivity(), /*this,*/ musicDetails.getResults(), R.layout.row_recycler));
    }

    @Override
    public void onFetchDataError(String error) {

    }
}
