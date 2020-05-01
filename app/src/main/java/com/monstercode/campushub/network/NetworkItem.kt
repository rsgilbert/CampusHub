package com.monstercode.campushub.network

import com.monstercode.campushub.database.DatabaseItem

data class NetworkItem(
    val _id: String,
    val name: String,
    val price: Int,
    val pictures: List<String>
) {
    fun asDatabaseModel() = DatabaseItem(
        _id = _id,
        name = name,
        price = price
    )
}

fun List<NetworkItem>.asDatabaseModel() = map { it.asDatabaseModel() }