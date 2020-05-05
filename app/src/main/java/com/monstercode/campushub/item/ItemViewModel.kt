package com.monstercode.campushub.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.Update
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch
import java.io.InputStream

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val itemIdLiveData = MutableLiveData<String>()

    val popToItemListLiveData = MutableLiveData<Boolean>()

    val navigateToUpdateLiveData = MutableLiveData<Update>()

    val navigateToPictureLiveData = MutableLiveData<String>()

    var itemLiveData = Transformations.switchMap(itemIdLiveData) {
        repository.getItem(it)
    }

    fun setItemIdLiveData(itemId: String) {
        itemIdLiveData.value = itemId
    }

    private fun popToItemListStart() {
        popToItemListLiveData.value = true
    }


    fun popToItemListComplete() {
        popToItemListLiveData.value = null
    }

    fun deleteItem() {
        viewModelScope.launch {
            itemIdLiveData.value?.let {
                val isDeleted = repository.deleteItem(it)
                if (isDeleted == true) {
                    popToItemListStart()
                }
            }
        }
    }

    fun navigateToUpdateNameStart() {
        navigateToUpdateLiveData.value = Update.NAME
    }

    fun navigateToUpdatePriceStart() {
        navigateToUpdateLiveData.value = Update.PRICE
    }

    fun navigateToUpdateComplete() {
        navigateToUpdateLiveData.value = null
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            itemLiveData.value?.let {
                repository.updateName(name, it)
            }
        }
    }

    fun updatePrice(price: Int) {
        viewModelScope.launch {
            itemLiveData.value?.let {
                repository.updatePrice(price, it)
            }
        }
    }

    fun uploadPicture(inputStream: InputStream) {
        viewModelScope.launch {
            itemLiveData.value?.let {
                repository.uploadPicture(inputStream, it)
            }
        }
    }

    /**
     * navigate to picture methods
     */
    fun navigateToPictureStart(pictureId: String) {
        navigateToPictureLiveData.value = pictureId
    }

    fun navigateToPictureComplete() {
        navigateToPictureLiveData.value = null
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::ItemViewModel)
    }
}