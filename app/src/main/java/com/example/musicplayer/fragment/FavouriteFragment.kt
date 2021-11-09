package com.example.musicplayer.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.FavoriteAdapter
import com.example.musicplayer.adapter.PlayAdapter
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.database.FavoriteEntity
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.model.PlaylistModel
import com.example.musicplayer.utils.ItemClickListener

class FavouriteFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentFavouriteBinding
    lateinit var database: FavoriteDatabase
    lateinit var arrayList: List<FavoriteEntity>
    lateinit var adapter: SongAdapter
    private lateinit var playAdapter: List<PlaylistModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        database = FavoriteDatabase.getInstance(requireContext())

        arrayList = database.songDao().fetchSave()
        playAdapter = listOf(PlaylistModel("", "", ""))

        val a = database.songDao().setFavData(1)
        Log.d("Fav", "onCreateView: $a[0]")
        adapter = SongAdapter(requireContext(), arrayList, playAdapter, this)
        binding.recyclerFavFragment.setHasFixedSize(true)
        binding.recyclerFavFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerFavFragment.adapter = adapter

        return binding.root
    }

    override fun onItemClick(
        name: String,
        artists: String,
        path: String,
        stime: Int,
        etime: Int,
        player: MediaPlayer,
        song_index: Int,
        Pos: Int,
    ) {
        TODO("Not yet implemented")
    }
}