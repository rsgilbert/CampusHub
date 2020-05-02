package com.monstercode.campushub.orderlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.campushub.repository.OrderRepository
import com.monstercode.campushub.util.RefreshError
import com.monstercode.campushub.util.singleArgViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class OrderListViewModel(private val repository: OrderRepository) : ViewModel() {
    init {
        refreshOrders()
    }

    val orders = repository.orderList

    private fun refreshOrders() {
        viewModelScope.launch {
            try {
                repository.refreshOrders()
            } catch(e: RefreshError) {
                Timber.e("Error refreshing: ${e.message}")
            }
        }
    }


    companion object {
        /**
         * Factory for creating [ListViewModel]
         * @param arg the repository to pass to [ListViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::OrderListViewModel)
    }
}