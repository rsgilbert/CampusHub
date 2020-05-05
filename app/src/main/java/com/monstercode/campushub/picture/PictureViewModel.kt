package com.monstercode.campushub.picture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.monstercode.campushub.repository.PictureRepository
import com.monstercode.campushub.util.singleArgViewModelFactory

class PictureViewModel(repository: PictureRepository) : ViewModel() {
    val pictureIdLiveData = MutableLiveData<String>()

    fun setPictureIdLiveData(pictureId: String) {
        pictureIdLiveData.value = pictureId
    }

    var pictureLiveData = Transformations.switchMap(pictureIdLiveData) {
        repository.getPicture(it)
    }

    fun deletePicture() {

    }


    companion object {
        val FACTORY = singleArgViewModelFactory(::PictureViewModel)
    }
}

