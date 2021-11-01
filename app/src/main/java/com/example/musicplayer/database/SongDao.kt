package com.example.musicplayer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {

    @Insert
    fun addData(favoriteEntity: FavoriteList?)

    @Query("SELECT * FROM favoritelist")
    fun getRecent(): List<RecentList>

    @Query("select * from favoritelist WHERE fav=:Fav")
    fun getFavoriteData(Fav: Boolean): List<FavoriteList?>?

    @Query("select * from favoritelist WHERE id=:Id")
    fun getRecentData(Id: Boolean): List<RecentList?>?

    @Delete
    fun delete(favoriteEntity: FavoriteList?)

//    @Query("select * from favoritelist")
//    fun getFavoriteData(): List<FavoriteList?>?
//
//    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
//    fun isFavorite(id: Int): Int

    @Query("UPDATE favoritelist SET fav=:Fav WHERE id=:Id")
    fun setFav(Fav: Boolean, Id: Int)

    @Query("SELECT * FROM favoritelist WHERE name=:Name")
    fun getId(Name: String?): Int

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: String): Int
}