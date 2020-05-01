package com.monstercode.campushub.domain

data class Order(
    val _id: String,
    val itemId: String,
    val time: Long,
    val phoneNumber: String,
    val hall: String,
    val completed: Boolean
)