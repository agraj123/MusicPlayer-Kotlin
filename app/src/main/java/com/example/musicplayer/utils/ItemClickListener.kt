package com.example.musicplayer.utils

import android.media.MediaPlayer

interface ItemClickListener {
    fun onItemClick(name:String,artists:String,path:String,stime:Int,etime:Int,player:MediaPlayer)
}