package com.monstercode.campushub.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val itemIdLiveData = MutableLiveData<String>()

    val popToItemListLiveData = MutableLiveData<Boolean>()

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

    companion object {
        val FACTORY = singleArgViewModelFactory(::ItemViewModel)
    }
}