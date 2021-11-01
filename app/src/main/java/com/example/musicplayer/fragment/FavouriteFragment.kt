package com.example.musicplayer.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.adapter.FavoriteAdapter
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.database.FavoriteList
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.model.PlaylistModel
import com.example.musicplayer.utils.ItemClickListener

class FavouriteFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentFavouriteBinding
    lateinit var database: FavoriteDatabase
    lateinit var model: ArrayList<PlaylistModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)

        database = FavoriteDatabase.getInstance(requireContext())
        val a = database.songDao().getFavoriteData(false)
        Log.d("TAG", "onCreateView: $a[0]")
        binding.recyclerFavFragment.setHasFixedSize(true)
        binding.recyclerFavFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerFavFragment.adapter = SongAdapter(requireContext(), model, this)

        return binding.root
    }

    override fun onItemClick(
        name: String,
        artists: String,
        path: String,
        stime: Int,
        etime: Int,
        player: MediaPlayer,
    ) {
        TODO("Not yet implemented")
    }
}