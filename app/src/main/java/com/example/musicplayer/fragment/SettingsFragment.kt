package com.example.musicplayer.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.Song
import com.example.musicplayer.adapter.SettingAdapter
import com.example.musicplayer.databinding.FragmentRecentBinding
import com.example.musicplayer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

//    var adapter: SettingAdapter? = null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        val recyclerView = binding.root.findViewById(R.id.recyclerSettingsFragment) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val songs = ArrayList<Song>()
        songs.add(Song("PlayList"))
        songs.add(Song("Artist"))
        songs.add(Song("Privacy and Security"))
        songs.add(Song("About"))

        val adapter = SettingAdapter(songs)
        recyclerView.adapter = adapter

//        val setting: ArrayList<String> = ArrayList()
//        setting.add("PlayList")
//        setting.add("Artist")
//        setting.add("Privacy and Security")
//        setting.add("About")
//
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerHomeFragment)
//        if (recyclerView != null) {
//            recyclerView.layoutManager = LinearLayoutManager(context)
//        }
//        adapter = SettingAdapter(context, setting)
////        adapter!!.setClickListener(this)
////        if (recyclerView != null) {
////            recyclerView.adapter = adapter
////        }

        return binding.root
    }
}