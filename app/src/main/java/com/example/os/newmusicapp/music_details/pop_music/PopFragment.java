package com.example.os.newmusicapp.music_details.pop_music;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.os.newmusicapp.R;
import com.example.os.newmusicapp.music_details.adapter.MusicAdapter;
import com.example.os.newmusicapp.data_model.network.model.MusicDetails;
import com.example.os.newmusicapp.data_model.network.services.IRequestInterface;
import com.example.os.newmusicapp.data_model.network.services.ServiceConnection;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopFragment extends Fragment {

    private IRequestInterface requestInterface;
    private RecyclerView recyclerView;

    // Swipe to refresh is declared
    private SwipeRefreshLayout refreshLayoutPop;

    // RxJava to make sure the app doesn't crash if the user
    // closes the app before the request from the api is handles
    private CompositeDisposable compositeDisposable;


    public PopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Retains the state when orientation is changed doesn't work if its in a back stack
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialised the declared variables
        requestInterface = ServiceConnection.getConnection();

        recyclerView = (RecyclerView) view.findViewById(R.id.rvPopContainer);
        // set the layout for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // composite disposable
        compositeDisposable = new CompositeDisposable();

        // call service
        callService();

        // setting the refresh function
        refreshLayoutPop = view.findViewById(R.id.swipeRefreshPop);
        refreshLayoutPop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        if ((compositeDisposable != null) && (!compositeDisposable.isDisposed())){
            compositeDisposable.clear();
        }
    }

    /**
     * displayPopResults gathers the data from the API and displays in recycler view
     * if there are any errors in retrieving data it throws an error and prints in the Log
     * swipe to refresh is turned off after the date is refreshed and also if data cannot be reached
     */
    private void displayPopResults(){
        compositeDisposable.add(
                requestInterface.getPopDetails()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<MusicDetails>() {
                                       @Override
                                       public void accept(MusicDetails musicDetails) throws Exception {
                                           // code to process information
                                           recyclerView.setAdapter(new MusicAdapter(getActivity(), musicDetails.getResults(), R.layout.row_recycler));
                                           // stop refreshing after the data is been displayed
                                           refreshLayoutPop.setRefreshing(false);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        // Error Code
                                        Log.e("consumer__Info",throwable.getMessage());
                                        // stop refreshing is there is error in receiving data from the api
                                        refreshLayoutPop.setRefreshing(false);
                                    }
                                })
        );
    }

    /**
     * callService method listens to the Internet Connection and Toasts when there is no network
     * if Internet is available it calls the @displayPopResults
     * swipe to refresh is turned off if data cannot be reached
     */
    private void callService(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet)throws Exception{
                        if (isConnectedToInternet){
                            // display data from API
                            displayPopResults();
                        } else {
                            // check Realm database for data
//                            Toast.makeText(getActivity().getApplicationContext(), "NO NETWORK!", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("You don't have internet connection")
                                    .setTitle("Network Alert!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // stop refreshing is there is no network
                            refreshLayoutPop.setRefreshing(false);
                        }
                    }
                });
    }
}
