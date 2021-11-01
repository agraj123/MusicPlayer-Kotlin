package com.example.musicplayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentlist")
data class RecentList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "artist")
    var artist: String?,

    @ColumnInfo(name = "name")
    var name: String?,
) {

}