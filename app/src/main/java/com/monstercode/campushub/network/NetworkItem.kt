package com.monstercode.campushub.network

import com.monstercode.campushub.database.DatabaseItem

data class NetworkItem(
    val _id: String,
    val name: String,
    val price: Int
) {
    fun asDatabaseModel() = DatabaseItem(
        _id = _id,
        name = name,
        price = price
    )

    // may be confusing and lead to autocomplete mistakes so I have commented it out
//    fun asDomainModel() = asDatabaseModel().asDomainModel()
}

fun List<NetworkItem>.asDatabaseModel() = map { it.asDatabaseModel() }


