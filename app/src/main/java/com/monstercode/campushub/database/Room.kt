package com.monstercode.campushub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseItem::class, DatabaseOrder::class, DatabasePicture::class], version = 1)
abstract class CampusHubDatabase: RoomDatabase() {
    abstract val itemDao: ItemDao
    abstract val orderDao: OrderDao
    abstract val pictureDao: PictureDao
}

private lateinit var INSTANCE: CampusHubDatabase

fun getDatabase(context: Context): CampusHubDatabase {
    synchronized(CampusHubDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                CampusHubDatabase::class.java,
                "CampusHubDatabase").build()
        }
    }
    return INSTANCE
}