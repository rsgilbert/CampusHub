package com.monstercode.campushub.database

import androidx.room.Entity
import com.monstercode.campushub.domain.Picture

@Entity
data class DatabasePicture(
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