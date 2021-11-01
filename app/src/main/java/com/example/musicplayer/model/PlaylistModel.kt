package com.example.musicplayer.model

import android.net.Uri

class PlaylistModel(var name:String, var artists:String, var path: String)
{
    fun getSong():String{
        return this.name
    }
    fun setSong(name: String){
        this.name=name
    }
    fun getSongArtists():String{
        return artists
    }
    fun setSongArtists(artists: String){
        this.artists=artists
    }
    fun getSongpath():String{
        return path
    }
    fun setSongpath(path: String){
        this.path=path
    }
}