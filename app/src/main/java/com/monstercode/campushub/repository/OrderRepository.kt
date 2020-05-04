package com.monstercode.campushub.repository

import androidx.lifecycle.Transformations
import com.monstercode.campushub.database.OrderDao
import com.monstercode.campushub.database.asDomainModel
import com.monstercode.campushub.network.NetworkOrder
import com.monstercode.campushub.network.asDatabaseModel
import com.monstercode.campushub.network.getNetworkService
import timber.log.Timber

/**
 * Repository for fetching data from network and room.
 */

class OrderRepository(private val orderDao: OrderDao) {
    val orderList = Transformations.map(orderDao.orderListLiveData) {
        it.asDomainModel()
    }

    suspend fun refreshOrders() {
        try {
            val orders: List<NetworkOrder> = getNetworkService().fetchOrders()
            orderDao.insertAll(orders.asDatabaseModel())
        } catch (cause: Throwable) {
            Timber.e("Unable to refresh: $cause")
//            throw RefreshError("Unable to fetch chapters", cause)
        }
    }
}


