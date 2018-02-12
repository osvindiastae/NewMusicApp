package com.example.os.newmusicapp.music_details.adapter;

/**
 * Created by Os on 10/02/2018.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.os.newmusicapp.R;
import com.example.os.newmusicapp.music_details.MusicItemClickListener;
import com.example.os.newmusicapp.music_details.model.MusicDetails;
import com.example.os.newmusicapp.music_details.model.MusicResults;
import com.example.os.newmusicapp.services.ApiList;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * MusicAdapter is used to display data from the API to the corresponding Fragment
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private FragmentActivity activity;
    private Consumer<MusicDetails> consumer;
    private List<MusicResults> results;
    private int row_recycler;

    public MusicAdapter(FragmentActivity activity, Consumer<MusicDetails> consumer, List<MusicResults> results, int row_recycler) {
        this.activity = activity;
        this.consumer = consumer;
        this.results = results;
        this.row_recycler = row_recycler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(row_recycler, parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvArtistName.setText(results.get(position).getArtistName());
        holder.tvCollectionName.setText(results.get(position).getCollectionName());

        // Null pointer exception
        String trackPrice = "N/A";
        if (results.get(position).getTrackPrice() != null){
            trackPrice = results.get(position).getTrackPrice().toString();
        }

        holder.tvTrackPrice.setText(ApiList.CURRENCY_GBP + trackPrice);
        Picasso.with(activity)
                .load(results.get(position).getArtworkUrl60())
                .resize(200, 200)
                .centerCrop()
                .into(holder.ivArtwork);

        /**
         * when the item is clisked opens a alert dialog and plays the sample music
         */
        holder.callOnItem(new MusicItemClickListener() {
            @Override
            public void onCLick(View view, int position) {

                // Music Stream Variables
                Uri myUri = Uri.parse(results.get(position).getPreviewUrl());
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(activity, myUri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(results.get(position).getArtistName() + " - " + results.get(position).getTrackName())
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mediaPlayer.stop();
                            }
                        })
                        .setTitle("Sample Music");
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
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
