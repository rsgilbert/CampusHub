package com.monstercode.campushub.picture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.repository.PictureRepository
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class PictureViewModel(private val repository: PictureRepository) : ViewModel() {
    //    private val pictureIdLiveData = MutableLiveData<String>()
    val itemIdLiveData = MutableLiveData<String>()

    val pictureListLiveData = Transformations.map(itemIdLiveData) {
        repository.getItemPictures(it)
    }

//    fun setPictureIdLiveData(pictureId: String) {
//        pictureIdLiveData.value = pictureId
//    }

    fun setItemIdLiveData(itemId: String) {
        itemIdLiveData.value = itemId
    }

    fun deletePicture() {
        viewModelScope.launch {
//            pictureIdLiveData.value?.let {
//                repository.deletePicture(it)
//            }
        }
    }


    companion object {
        val FACTORY = singleArgViewModelFactory(::PictureViewModel)
    }
}

