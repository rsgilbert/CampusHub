package com.monstercode.campushub.repository

import androidx.lifecycle.Transformations
import com.monstercode.campushub.database.PictureDao

class PictureRepository(private val pictureDao: PictureDao) {
    fun getPicture(pictureId: String) =
        Transformations.map(pictureDao.getPicture(pictureId)) { it.asDomainModel() }


}