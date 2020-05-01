package com.monstercode.campushub.network

import com.monstercode.campushub.database.DatabaseOrder

data class NetworkOrder(
    val _id: String,
    val itemId: String,
    val time: Long,
    val phoneNumber: String,
    val hall: String,
    val completed: Boolean
) {
    fun asDatabaseModel() = DatabaseOrder(
        _id = _id,
        itemId = itemId,
        phoneNumber = phoneNumber,
        hall = hall,
        time = time,
        completed = completed
    )
}

fun List<NetworkOrder>.asDatabaseModel() = map { it.asDatabaseModel() }