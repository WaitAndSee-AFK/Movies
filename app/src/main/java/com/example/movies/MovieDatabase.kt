package com.example.movies

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private var instance: MovieDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "movie.db"

        fun getInstance(application: Application) : MovieDatabase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                return Room.databaseBuilder(
                    application,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
            }
        }
    }

    abstract fun moviesDao(): MoviesDao
}