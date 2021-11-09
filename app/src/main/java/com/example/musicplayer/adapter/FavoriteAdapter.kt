package com.example.musicplayer.adapter

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.database.FavoriteEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.musicplayer.R
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.model.PlaylistModel

class FavoriteAdapter(favoriteEntityEnteties: List<FavoriteEntity?>?, context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val favoriteEntityEnteties: List<FavoriteEntity?>? = favoriteEntityEnteties
    var context: Context = context
    var arrayList = favoriteEntityEnteties
    lateinit var database: FavoriteDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.favouritelayout, parent, false)
        database = FavoriteDatabase.getInstance(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = arrayList!![position]!!.artist
        holder.artist.text = arrayList!![position]!!.name

        holder.fav.setOnClickListener {
            val delete = database.songDao().delete(arrayList!![position]?.name)
            Log.d("TAG", "onBindViewHolder: Deleted item is : $delete")

            holder.fav.visibility = View.GONE
            holder.favFilled.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return favoriteEntityEnteties!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.textTitleFavDesign)
        var artist: TextView = itemView.findViewById(R.id.favArtist)
        var fav: ImageView = itemView.findViewById(R.id.ImgFavDesign)
        var favFilled: ImageView = itemView.findViewById(R.id.ImgFavBorderLayout)
    }

}