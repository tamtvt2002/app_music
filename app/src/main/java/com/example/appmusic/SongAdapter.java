package com.example.appmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{

    private Context mContext;
    private List<Song> mListSong;

    public SongAdapter(Context mContext, List<Song> mListSong) {
        this.mListSong = mListSong;
        this.mContext = mContext;
    }

    public void setData(List<Song> list){
        this.mListSong = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {

        final Song song = mListSong.get(position);
        if(song == null){
            return;
        }

        holder.imgSong.setImageResource(song.getFileAvatar());
        holder.nameSong.setText(song.getTitle() + " - " + song.getAuthor());

        holder.layoutItemSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenSong(song);
            }
        });
    }

    private void onClickListenSong(Song song){
        Intent intent = new Intent(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object song", song);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        if(mListSong != null){
            return mListSong.size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSong;
        private TextView nameSong;
        private RelativeLayout layoutItemSong;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.imgAvatarSong);
            nameSong = itemView.findViewById(R.id.textNameSong);
            layoutItemSong = itemView.findViewById(R.id.layoutItemSong);
        }
    }
}
