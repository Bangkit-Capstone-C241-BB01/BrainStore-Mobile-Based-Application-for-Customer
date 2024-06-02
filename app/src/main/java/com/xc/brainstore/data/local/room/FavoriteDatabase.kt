package com.xc.brainstore.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xc.brainstore.data.local.entity.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "favorite_database"
                ).build().also { INSTANCE = it }
            }
        }
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: FavoriteDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): FavoriteDatabase {
//            if (INSTANCE == null) {
//                synchronized(FavoriteDatabase::class.java) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        FavoriteDatabase::class.java, "favorite_database"
//                    )
//                        .build()
//                }
//            }
//            return INSTANCE as FavoriteDatabase
//        }
//    }
}