package com.example.musicplayer.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.Song
import com.squareup.picasso.Picasso

class RecentAdapter(val songList: ArrayList<Song>) :
    RecyclerView.Adapter<RecentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textview = itemView.findViewById(R.id.songNameRecent) as TextView
        val image = itemView.findViewById(R.id.imageRecentLayout) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recentlayout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song: Song = songList[position]
        holder.textview.text = song.list
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}