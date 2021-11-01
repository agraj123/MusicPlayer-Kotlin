package com.example.musicplayer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {

    @Insert
    fun addData(favoriteEntity: FavoriteList?)

    @Query("select * from favoritelist WHERE fav=:Fav")
    fun getFavoriteData(Fav: Boolean): List<FavoriteList?>?

//    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
//    fun isFavorite(id: String): Int

    @Delete
    fun delete(favoriteEntity: FavoriteList?)

    @Query("UPDATE favoritelist SET fav=:Fav WHERE id=:Id")
    fun setFav(Fav:Boolean , Id:Int)

    @Query("SELECT id FROM favoritelist WHERE name=:Name")
    fun getId(Name:String?) :Int?
}