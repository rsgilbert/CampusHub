package com.monstercode.campushub.database

import androidx.room.Embedded
import androidx.room.Relation
import com.monstercode.campushub.domain.Item

data class DatabaseCompleteItem(
    @Embedded
    val item: DatabaseItem,

    @Relation(parentColumn = "_id", entityColumn = "itemId")
    val pictures: List<DatabasePicture>
) {
    fun asDomainModel() = Item(
        _id = item._id,
        name = item.name,
        price = item.price,
        pictures = pictures.asDomainModel()
    )
}

fun List<DatabaseCompleteItem>.asDomainModel() = map { it.asDomainModel() }