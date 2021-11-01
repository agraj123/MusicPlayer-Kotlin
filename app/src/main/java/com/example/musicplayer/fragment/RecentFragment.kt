package com.example.musicplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.Song
import com.example.musicplayer.adapter.RecentAdapter
import com.example.musicplayer.adapter.SettingAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.databinding.FragmentRecentBinding

class RecentFragment : Fragment() {
    private lateinit var binding: FragmentRecentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecentBinding.inflate(layoutInflater, container, false)

        val recyclerView = binding.root.findViewById(R.id.recyclerRecentFragment) as RecyclerView

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val songs = ArrayList<Song>()
        songs.add(Song("Android"))
        songs.add(Song("Instrumental"))
        songs.add(Song("Music"))
        songs.add(Song("Arijit Singh"))
        songs.add(Song("A R Rehman"))
        songs.add(Song("Hindi POP"))
        songs.add(Song("iPhone"))

        val adapter = RecentAdapter(songs)
        recyclerView.adapter = adapter

        return binding.root
    }
}