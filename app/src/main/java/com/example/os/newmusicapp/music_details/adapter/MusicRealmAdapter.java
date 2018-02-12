package com.example.os.newmusicapp.music_details.adapter;

/**
 * Created by Os on 11/02/2018.
 */

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.os.newmusicapp.R;
import com.example.os.newmusicapp.music_details.MusicItemClickListener;
import com.example.os.newmusicapp.realmdb.model.MusicData;
import com.example.os.newmusicapp.services.ApiList;

import java.util.ArrayList;

/**
 * MusicRealmAdapter is used to display data from the Realm Database to the RockFragment
 */
public class MusicRealmAdapter extends RecyclerView.Adapter<MusicRealmAdapter.MyViewHolder> {
    private FragmentActivity activity;
    private ArrayList<MusicData> realmData;
    private int row_recycler;

    public MusicRealmAdapter(FragmentActivity activity, ArrayList<MusicData> realmData, int row_recycler) {
        this.activity = activity;
        this.realmData = realmData;
        this.row_recycler = row_recycler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(row_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvArtistName.setText(realmData.get(position).getArtistName());
        holder.tvCollectionName.setText(realmData.get(position).getCollectionName());

        // Null pointer exception
        String trackPrice = "N/A";
        if (realmData.get(position).getTrackPrice() != null){
            trackPrice = realmData.get(position).getTrackPrice().toString();
        }

        holder.tvTrackPrice.setText(ApiList.CURRENCY_GBP + trackPrice);

        holder.callOnItem(new MusicItemClickListener() {
            @Override
            public void onCLick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Music sample is not available where there is no network")
                        .setTitle("Alert");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return realmData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivArtwork;
        private TextView tvArtistName;
        private TextView tvCollectionName;
        private TextView tvTrackPrice;

        private MusicItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivArtwork = (ImageView) itemView.findViewById(R.id.ivArtwork);
            tvArtistName = (TextView) itemView.findViewById(R.id.tvArtistName);
            tvCollectionName = (TextView) itemView.findViewById(R.id.tvCollectionName);
            tvTrackPrice = (TextView) itemView.findViewById(R.id.tvTrackPrice);

            itemView.setOnClickListener(this);
        }

        private void callOnItem(MusicItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onCLick(view, getPosition());
        }
    }
}