package com.monstercode.campushub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {
    @get:Query("SELECT * FROM DatabaseItem")
    val itemListLiveData : LiveData<List<DatabaseItem>>

    @Query("SELECT * FROM DatabaseITEM WHERE _id = :itemId")
    fun getItem(itemId: String) : LiveData<DatabaseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DatabaseItem>)


}