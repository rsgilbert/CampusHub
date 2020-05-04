package com.monstercode.campushub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureDao {
    @get:Query("SELECT * FROM DatabasePicture")
    val pictureListLiveData : LiveData<List<DatabasePicture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pictures: List<DatabasePicture>)


}