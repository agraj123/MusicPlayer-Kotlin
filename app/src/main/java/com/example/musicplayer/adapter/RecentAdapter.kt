package com.example.musicplayer.adapter

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.database.FavoriteList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.musicplayer.R
import com.example.musicplayer.database.RecentList
import com.example.musicplayer.model.PlaylistModel
import com.squareup.picasso.Picasso

class RecentAdapter(recentListEntity: List<FavoriteList?>?, context: Context) :
    RecyclerView.Adapter<RecentAdapter.ViewHolder>() {
    private val recentListEntity: List<FavoriteList?>? by lazy { recentListEntity }
    var context: Context = context
    var arrayList = recentListEntity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recentlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = arrayList!![position]!!.name
    }

    override fun getItemCount(): Int {
        return recentListEntity!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.songNameRecent)

    }

}