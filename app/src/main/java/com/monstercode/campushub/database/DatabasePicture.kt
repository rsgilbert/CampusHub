package com.monstercode.campushub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monstercode.campushub.domain.Picture

@Entity
data class DatabasePicture(
    @PrimaryKey
    val _id: String,
    val pictureUrl: String,
    val itemId: String,
    val isProfile: Boolean
) {
    fun asDomainModel() = Picture(
        _id = _id,
        itemId = itemId,
        pictureUrl = pictureUrl,
        isProfile = isProfile
    )
}

fun List<DatabasePicture>.asDomainModel() = map { it.asDomainModel() }