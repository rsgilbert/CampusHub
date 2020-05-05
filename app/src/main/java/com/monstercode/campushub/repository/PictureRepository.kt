package com.monstercode.campushub.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.monstercode.campushub.database.PictureDao
import com.monstercode.campushub.database.asDomainModel
import com.monstercode.campushub.domain.Picture
import timber.log.Timber

class PictureRepository(val pictureDao: PictureDao) {
    val pictureLiveData = Transformations.map(pictureDao.pictureListLiveData) { it.asDomainModel() }
    fun getPicture(pictureId: String) =
        Transformations.map(pictureDao.getPicture(pictureId)) { it.asDomainModel() }

    fun getPictures(): LiveData<List<Picture>> {
        Timber.i("Picture Live data: " + pictureLiveData.value.toString())
        return pictureLiveData
    }

}