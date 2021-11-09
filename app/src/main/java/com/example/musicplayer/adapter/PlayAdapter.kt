package com.example.musicplayer.adapter

import android.annotation.SuppressLint
import android.content.Context

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
import com.example.musicplayer.database.FavoriteEntity
import com.example.musicplayer.model.PlaylistModel
import com.google.android.material.snackbar.Snackbar
import java.io.File

class PlayAdapter(
    private var context: Context,
    private var arrayList: List<FavoriteEntity>,
    private var clickListner: ItemClickListener,
) :
    RecyclerView.Adapter<PlayAdapter.MusicViewHolder>() {
    private var player: MediaPlayer = MediaPlayer()
    lateinit var model: ArrayList<FavoriteEntity>
    private var song_index = 0

    //    private lateinit var product: FavoriteEntity
    private lateinit var database: FavoriteDatabase

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview1 = itemView.findViewById<TextView>(R.id.textTitleDesignHome)!!
        var textvoew2 = itemView.findViewById<TextView>(R.id.artistDesign)!!
        var fav = itemView.findViewById<ImageView>(R.id.favlayout)
        var favFilled = itemView.findViewById<ImageView>(R.id.favFilled)
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

        val productList: FavoriteEntity = arrayList[position]
        holder.textview1.text = arrayList[position].name
        holder.textvoew2.text = arrayList[position].artist

        database = FavoriteDatabase.getInstance(context)

//        val save = productList.product_save
//
//        if (save == 0) {
//            holder.fav.setImageDrawable(AppCompatResources.getDrawable(context,
//                R.drawable.ic_favorite_border))
//        } else {
//            holder.fav.setImageDrawable(AppCompatResources.getDrawable(context,
//                R.drawable.ic_favorite))
//        }
//
//        holder.fav.setOnClickListener {
//            if (productList.product_save == 1) {
//                val updateProduct =
//                    FavoriteEntity(0, arrayList[position].artist, arrayList[position].name,
//                        fav = false,
//                        rec = false,
//                        product_save = 0)
//                database.songDao().updateProduct(updateProduct)
//            } else {
//                val updateProduct =
//                    FavoriteEntity(0, arrayList[position].artist, arrayList[position].name,
//                        fav = false,
//                        rec = false,
//                        product_save = 1)
//                database.songDao().updateProduct(updateProduct)
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
//            Snackbar.make(it, "Added to Favourite", Snackbar.LENGTH_SHORT).show()
//            database.songDao().setFav(true, song)
//            Log.d("TAG", "onBindViewHolder: $song")
//        }
//
//        holder.itemView.setOnClickListener {
//            Log.d("TAG", "onBindViewHolder: ${it.id}")
//        }

//        holder.itemView.setOnClickListener {
//            try {
//                val recent = database.songDao().setRecentData(true, arrayList[position].name)
//                Log.d("TAG", "onBindViewHolder: $recent")
////                val path = arrayList[position].path
////                val file = File(path)
//                song_index = position
//                if (file.exists()) {
//                    player.setDataSource(file.path)
//                    player.prepare()
////                    player.start()
//                    val stime = player.currentPosition
//                    val etime = player.duration
//                    Log.d("TAG", "onBindViewHolder: stime $stime")
//                    Log.d("TAG", "onBindViewHolder: etime $etime")
//                    Log.d("TAG", "onBindViewHolder: player " + player.isPlaying)
//
//                    arrayList[position].name?.let { it1 ->
//                        arrayList[position].artist?.let { it2 ->
//                            clickListner.onItemClick(
//                                it1,
//                                it2,
//                                path,
//                                stime,
//                                etime,
//                                player,
//                                song_index,
//                                position
//                            )
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d("TAG", "onBindViewHolder: error$e")
//            }
//        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun filterList(filterllist: ArrayList<FavoriteEntity>) {
        arrayList = filterllist
        notifyDataSetChanged()
    }
}