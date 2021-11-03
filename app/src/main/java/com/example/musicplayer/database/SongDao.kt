package com.example.musicplayer.database

import androidx.room.*

@Dao
interface SongDao {

    @Insert
    fun addData(favoriteEntity: FavoriteList?)

//    @Query("SELECT * FROM favoritelist")
//    fun getRecent(): List<FavoriteList>

    @Query("SELECT name FROM favoritelist WHERE fav=:Fav")
    fun getName(Fav: Boolean?): List<String>

    @Query("select * from favoritelist WHERE fav=:Fav")
    fun getFavoriteData(Fav: Boolean?): List<FavoriteList>

    @Query("select * from favoritelist WHERE rec=:Rec")
    fun getRecentData(Rec: Boolean?): List<FavoriteList>

    @Query("UPDATE favoritelist SET rec=:Rec WHERE name=:Name")
    fun setRecentData(Rec: Boolean?, Name: String?)

    @Delete
    fun delete(favoriteEntity: FavoriteList?)

    @Query("UPDATE favoritelist SET fav=:Fav WHERE id=:Id")
    fun setFav(Fav: Boolean?, Id: Int)

    @Query("SELECT * FROM favoritelist WHERE name=:Name")
    fun getId(Name: String?): Int

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: String): Int

    @Update
    suspend fun updateProduct(favoriteList: FavoriteList)

}