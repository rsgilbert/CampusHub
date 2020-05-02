package com.monstercode.campushub.network

import com.monstercode.campushub.database.DatabasePicture

data class NetworkPicture(
    val _id: String,
    val pictureUrl: String,
    val itemId: String,
    val isProfile: Boolean
) {
    fun asDatabaseModel() = DatabasePicture(
        _id = _id,
        pictureUrl = pictureUrl,
        itemId = itemId,
        isProfile = isProfile
    )
}