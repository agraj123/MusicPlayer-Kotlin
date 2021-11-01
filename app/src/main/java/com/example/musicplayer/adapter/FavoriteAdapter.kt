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
import com.squareup.picasso.Picasso

class FavoriteAdapter(favoriteListEnteties: List<FavoriteList>, context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val favoriteListEnteties: List<FavoriteList> = favoriteListEnteties
    var context: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.favouritelayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fl: FavoriteList = favoriteListEnteties[position]
        holder.name.setText(fl.id)
        holder.artist.text = fl.artist
    }

    override fun getItemCount(): Int {
        return favoriteListEnteties.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.textTitleFavDesign)
        var artist: TextView = itemView.findViewById(R.id.favArtist)

    }

}

//class FavoriteAdapter(private val favoriteLists: ArrayList<FavoriteList>, context: Context) :
//    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
//    var context: Context = context
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
//        val view: View =
//            LayoutInflater.from(viewGroup.context)
//                .inflate(R.layout.favouritelayout, viewGroup, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
//        val fl = favoriteLists[i]
//        Picasso.with(context).load(fl.image).into(viewHolder.img)
//        viewHolder.tv.text = fl.name
//    }
//
//    override fun getItemCount(): Int {
//        return favoriteLists.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var img: ImageView = itemView.findViewById(R.id.imageFavLayout) as ImageView
//        var tv: TextView = itemView.findViewById(R.id.textTitleFavDesign)
//
//    }
//}