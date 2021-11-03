package com.example.musicplayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritelist")
data class FavoriteList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "artist")
    var artist: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "fav")
    var fav: Boolean,

    @ColumnInfo(name = "rec")
    val rec: Boolean,

    @ColumnInfo(name = "product_save")
    val product_save : Int
) {

}