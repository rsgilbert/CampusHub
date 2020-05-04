package com.monstercode.campushub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Transaction
    @Query("SELECT * FROM DatabaseItem")
    fun getItems(): LiveData<List<DatabaseCompleteItem>>

    @Transaction
    @Query("SELECT * FROM DatabaseItem WHERE _id = :itemId LIMIT 1")
    fun getItem(itemId: String): LiveData<DatabaseCompleteItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DatabaseItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneItem(item: DatabaseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictures(pictures: List<DatabasePicture>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePicture(picture: DatabasePicture)

    @Query("DELETE FROM DatabaseItem WHERE _id=:itemId")
    suspend fun deleteOne(itemId: String)


}