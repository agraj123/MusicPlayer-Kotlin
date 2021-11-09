package com.example.musicplayer.adapter

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.database.FavoriteEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayer.R

class RecentAdapter(recentEntityEntity: List<FavoriteEntity?>?, context: Context) :
    RecyclerView.Adapter<RecentAdapter.ViewHolder>() {
    private val recentEntityEntity: List<FavoriteEntity?>? by lazy { recentEntityEntity }
    var context: Context = context
    var arrayList = recentEntityEntity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recentlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = arrayList!![position]!!.name
    }

    override fun getItemCount(): Int {
        return recentEntityEntity!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.songNameRecent)

    }

}