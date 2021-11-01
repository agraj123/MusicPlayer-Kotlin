package com.example.musicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityCurrentSongBinding

class CurrentSong : AppCompatActivity() {

    lateinit var binding: ActivityCurrentSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentSongBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}