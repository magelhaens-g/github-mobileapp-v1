package com.dvlpr.githubapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDatabase: RoomDatabase() {
    companion object{
        var INSTANCE : FavoriteUserDatabase? = null

        fun getDatabase(context: Context): FavoriteUserDatabase?{
            if (INSTANCE == null) {
                synchronized(FavoriteUserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteUserDatabase::class.java,"user_database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favUserDao(): FavoriteUserDao
}