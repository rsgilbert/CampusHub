package com.monstercode.campushub.repository

import androidx.lifecycle.Transformations
import com.monstercode.campushub.database.PictureDao
import com.monstercode.campushub.database.asDomainModel
import com.monstercode.campushub.network.getNetworkService

class PictureRepository(val pictureDao: PictureDao) {
    fun getPicture(pictureId: String) =
        Transformations.map(pictureDao.getPicture(pictureId)) { it?.asDomainModel() }

    suspend fun deletePicture(pictureId: String) {
        try {
            val networkPicture = getNetworkService().deletePicture(pictureId)
            pictureDao.deleteOne(networkPicture._id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getItemPictures(itemId: String) =
        Transformations.map(pictureDao.getItemPictures(itemId)) { it.asDomainModel() }


}