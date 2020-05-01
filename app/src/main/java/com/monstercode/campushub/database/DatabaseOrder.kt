package com.monstercode.campushub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.domain.Order

@Entity
data class DatabaseOrder(
    @PrimaryKey
    val _id: String,
    val itemId: String,
    val time: Long,
    val phoneNumber: String,
    val hall: String,
    val completed: Boolean
) {
    fun asDomainModel() = Order(
        _id = _id, itemId = itemId, time = time, phoneNumber = phoneNumber, hall = hall, completed = completed
    )
}

/**
 * Map DatabaseItem to domain object
 */
fun List<DatabaseOrder>.asDomainModel() = map { it.asDomainModel() }
