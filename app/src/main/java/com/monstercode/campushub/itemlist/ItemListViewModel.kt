package com.monstercode.campushub.itemlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.RefreshError
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemListViewModel(private val repository: ItemRepository) : ViewModel() {
    init {
        refreshItems()
    }

    val items = repository.itemList

    private fun refreshItems() {
        viewModelScope.launch {
            try {
                repository.refreshItems()
            } catch(e: RefreshError) {
                Timber.e("Error refreshing: ${e.message}")
            }
        }
    }


    companion object {
        val FACTORY = singleArgViewModelFactory(::ItemListViewModel)
    }
}