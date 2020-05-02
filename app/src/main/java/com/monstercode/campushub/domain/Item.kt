package com.monstercode.campushub.domain

data class Item(
    val _id: String,
    val name: String,
    val price: Int,
    val pictures: List<Picture>
)