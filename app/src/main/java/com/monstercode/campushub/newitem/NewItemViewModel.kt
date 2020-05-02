package com.monstercode.campushub.newitem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class NewItemViewModel(private val repository: ItemRepository) : ViewModel() {

    val navigateToItemItemIdLiveData = MutableLiveData<String>()

    fun postNewItem(name: String, price: Int) {
        viewModelScope.launch {
            val item = repository.postNewItem(name, price)
            item?.let {
                navigateToItemStart(item._id)
            }
        }
    }

    private fun navigateToItemStart(itemId: String) {
        navigateToItemItemIdLiveData.value = itemId
    }

    fun navigateToItemComplete() {
        navigateToItemItemIdLiveData.value = null
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::NewItemViewModel)
    }
}