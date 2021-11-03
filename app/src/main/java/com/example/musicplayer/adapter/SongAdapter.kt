package com.example.musicplayer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.utils.ItemClickListener
import com.example.musicplayer.R
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.database.FavoriteList
import com.example.musicplayer.model.PlaylistModel
import com.google.android.material.snackbar.Snackbar
import java.io.File

class SongAdapter(
    private var item: FavoriteList,
    itemView: View,
    private var context: Context,
    private var arrayList: ArrayList<PlaylistModel>,
    private var clickListner: ItemClickListener,
) :
    RecyclerView.Adapter<SongAdapter.MusicViewHolder>() {
    private var player: MediaPlayer = MediaPlayer()
    lateinit var database: FavoriteDatabase
    lateinit var model: ArrayList<PlaylistModel>
    private var song_index = 0

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview1 = itemView.findViewById<TextView>(R.id.textTitleDesignHome)!!
        var textvoew2 = itemView.findViewById<TextView>(R.id.artistDesign)!!
        var fav = itemView.findViewById<ImageView>(R.id.favlayout)
//        var favFilled = itemView.findViewById<ImageView>(R.id.favFilled)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.homelayout, parent, false)

        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MusicViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        val productList: PlaylistModel = arrayList[position]
        holder.textview1.text = arrayList[position].name
        holder.textvoew2.text = arrayList[position].artists

        database = FavoriteDatabase.getInstance(context)

        val save = item.product_save

        if (save == 0) {
            holder.fav.setImageDrawable(AppCompatResources.getDrawable(context,
                R.drawable.ic_favorite_border))
        } else {
            holder.fav.setImageDrawable(AppCompatResources.getDrawable(context,
                R.drawable.ic_favorite))
        }

//        holder.fav.setOnClickListener {
//            if (item.product_save == 1){
//                val updateProduct = FavoriteList(item.id,item.artist,item.name,item.fav,item.rec,0)
//                productViewModel.updateProduct(updateProduct)
//            }else {
//                val updateProduct = FavoriteList(item.id,item.artist,item.name,item.fav,item.rec,1)
//                productViewModel.updateProduct(updateProduct)
//            }
//        }

//        holder.fav.setOnClickListener {
//            val song = database.songDao().getId(arrayList[position].name)
//            Log.d("TAG", "onBindViewHolder: $song")
//
//            if (database.songDao().getName(true).contains(arrayList[position].name)) {
//                holder.fav.visibility = View.GONE
//                holder.favFilled.visibility = View.VISIBLE
//            } else {
//                holder.fav.visibility = View.VISIBLE
//                holder.favFilled.visibility = View.GONE
//            }
//            database.songDao().setFav(true, song)
//            Snackbar.make(it, "Added to Favourite", Snackbar.LENGTH_SHORT).show()
//            Log.d("TAG", "onBindViewHolder: $song")
//        }

        holder.itemView.setOnClickListener {
            try {
                val recent = database.songDao().setRecentData(true, arrayList[position].name)
                Log.d("TAG", "onBindViewHolder: $recent")
                val path = arrayList[position].path
                val file = File(path)
                song_index = position
                if (file.exists()) {
                    player.setDataSource(file.path)
                    player.prepare()
//                    player.start()
                    val stime = player.currentPosition
                    val etime = player.duration
                    Log.d("TAG", "onBindViewHolder: stime $stime")
                    Log.d("TAG", "onBindViewHolder: etime $etime")
                    Log.d("TAG", "onBindViewHolder: player " + player.isPlaying)

                    clickListner.onItemClick(
                        arrayList[position].name,
                        arrayList[position].artists,
                        path,
                        stime,
                        etime,
                        player,
                        song_index,
                        position
                    )
                }
            } catch (e: Exception) {
                Log.d("TAG", "onBindViewHolder: error$e")
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun filterList(filterllist: ArrayList<PlaylistModel>) {
        arrayList = filterllist
        notifyDataSetChanged()
    }
}