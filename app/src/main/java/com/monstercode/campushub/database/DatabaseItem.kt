package com.monstercode.campushub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monstercode.campushub.domain.Item

@Entity
data class DatabaseItem constructor(
    @PrimaryKey
    val _id: String,
    val name: String,
    val price: Int
) {
    fun asDomainModel() = Item(_id = _id, name = name, price = price, pictures = listOf())
}

/**
 * Map DatabaseItem to domain object
 */
fun List<DatabaseItem>.asDomainModel() = map { it.asDomainModel() }
