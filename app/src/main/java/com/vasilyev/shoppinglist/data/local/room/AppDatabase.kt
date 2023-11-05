package com.vasilyev.shoppinglist.data.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun shopListDao(): ShopListDao
    companion object{
        private const val DB_NAME = "shop_item.db"
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let{
                return it
            }

            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }

}