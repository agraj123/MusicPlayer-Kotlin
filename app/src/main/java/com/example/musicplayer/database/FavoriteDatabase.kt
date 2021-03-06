package com.example.musicplayer.database

import android.content.Context
import androidx.room.RoomDatabase

import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.flow.Flow

@Database(entities = [FavoriteEntity::class], version = 4)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {

        private var instance: FavoriteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FavoriteDatabase {
            instance = Room.databaseBuilder(
                context,
                FavoriteDatabase::class.java,
                "fav_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            return instance as FavoriteDatabase
        }
    }
}