package com.monstercode.campushub.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.singleArgViewModelFactory

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val itemIdLiveData = MutableLiveData<String>()

    var itemLiveData = Transformations.switchMap(itemIdLiveData) {
        repository.getItem(it)
    }

    fun setItemIdLiveData(itemId : String) {
        itemIdLiveData.value = itemId
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::ItemViewModel)
    }
}