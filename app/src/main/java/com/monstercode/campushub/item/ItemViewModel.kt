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

    val navigateToUpdateLiveData = MutableLiveData<Update>()

    val navigateToPictureLiveData = MutableLiveData<String>()

    var itemLiveData = Transformations.switchMap(itemIdLiveData) {
        repository.getItem(it)
    }

    fun setItemIdLiveData(itemId: String) {
        itemIdLiveData.value = itemId
    }

    fun deleteItem(): Boolean {
        var isDeleted = false
        viewModelScope.launch {
            itemIdLiveData.value?.let {
                isDeleted = repository.deleteItem(it) ?: false
            }
        }
        return isDeleted
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
    fun navigateToPictureStart() {
        navigateToPictureLiveData.value = itemIdLiveData.value
    }

    fun navigateToPictureComplete() {
        navigateToPictureLiveData.value = null
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::ItemViewModel)
    }
}