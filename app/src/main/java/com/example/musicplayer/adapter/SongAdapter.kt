package com.example.musicplayer.adapter

import android.content.Context

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.utils.ItemClickListener
import com.example.musicplayer.R
import com.example.musicplayer.activity.MainActivity
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.database.FavoriteList
import com.example.musicplayer.model.PlaylistModel
import com.squareup.picasso.Picasso
import java.io.File

class SongAdapter(
    private var context: Context,
    private var arrayList: ArrayList<PlaylistModel>,
    private var clickListner: ItemClickListener,
) :
    RecyclerView.Adapter<SongAdapter.MusicViewHolder>() {
    private var player: MediaPlayer = MediaPlayer()
    lateinit var database: FavoriteDatabase
    lateinit var model: ArrayList<PlaylistModel>

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview1 = itemView.findViewById<TextView>(R.id.textTitleDesignHome)!!
        var textvoew2 = itemView.findViewById<TextView>(R.id.artistDesign)!!
        var fav = itemView.findViewById<ImageView>(R.id.favlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.homelayout, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val productList: PlaylistModel = arrayList[position]
        holder.textview1.text = arrayList[position].name
        holder.textvoew2.text = arrayList[position].artists

        database = FavoriteDatabase.getInstance(context)
        database.songDao()
            .addData(FavoriteList(0, arrayList[position].name, arrayList[position].artists, false))

        holder.fav.setOnClickListener {
            val song = database.songDao().getId(arrayList[position].name)
            Log.d("TAG", "onBindViewHolder: $song ")

            if (song != null) {
                database.songDao().setFav(true, song)
                Toast.makeText(context, song, Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.setOnClickListener {
            try {
                val path = arrayList[position].path
                val file = File(path)
                if (file.exists()) {
                    player.setDataSource(file.path)
                    player.prepare()
                    player.start()
                    val stime = player.currentPosition
                    val etime = player.duration
                    Log.d("TAG", "onBindViewHolder: $stime")
                    Log.d("TAG", "onBindViewHolder: $etime")
                    Log.d("TAG", "onBindViewHolder: " + player.isPlaying)

                    clickListner.onItemClick(
                        arrayList[position].name,
                        arrayList[position].artists,
                        path,
                        stime,
                        etime,
                        player
                    )
                }
            } catch (e: Exception) {
                Log.d("TAG", "onBindViewHolder: " + e.message)
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
