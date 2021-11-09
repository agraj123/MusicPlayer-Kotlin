package com.example.musicplayer.database

import androidx.room.*
import com.example.musicplayer.model.PlaylistModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert
    fun addData(favoriteEntity: FavoriteEntity?)

//    @Query("SELECT * FROM favoritelist")
//    fun getRecent(): List<FavoriteList>

    @Query("SELECT name FROM favoritelist WHERE fav=:Fav")
    fun getName(Fav: Boolean?): List<String>

    @Query("select * from favoritelist WHERE fav=:Fav")
    fun getFavoriteData(Fav: Boolean?): List<FavoriteEntity>

    @Query("SELECT * FROM favoritelist WHERE product_save=:Name")
    fun setFavData(Name: Int?): List<FavoriteEntity>

    @Query("select * from favoritelist WHERE rec=:Rec")
    fun getRecentData(Rec: Boolean?): List<FavoriteEntity>

    @Query("UPDATE favoritelist SET rec=:Rec WHERE name=:Name")
    fun setRecentData(Rec: Boolean?, Name: String?)

//    @Delete
//    fun delete(favoriteEntity: FavoriteEntity?)

    @Query("DELETE FROM favoritelist WHERE name=:Name")
    fun delete(Name: String?): Int

    @Query("UPDATE favoritelist SET fav=:Fav WHERE id=:Id")
    fun setFav(Fav: Boolean?, Id: Int)

    @Query("SELECT * FROM favoritelist WHERE name=:Name")
    fun getId(Name: String?): Int

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: String): Int

    @Update
    fun updateProduct(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoritelist")
    fun fetchList(): List<FavoriteEntity>

    @Query("SELECT * FROM favoritelist WHERE product_save=1")
    fun fetchSave(): List<FavoriteEntity>

    @Query("SELECT product_save FROM favoritelist where id= :songId")
    fun getProductSave(songId: Int?): Int

    @Query("SELECT * FROM favoritelist where product_save= :save and id= :userId ORDER BY id")
    fun fetchWishList(save: String, userId: Int): List<FavoriteEntity>

    @Query("SELECT product_save FROM favoritelist WHERE id=:Id")
    fun getFavId(Id: Int): Int
}