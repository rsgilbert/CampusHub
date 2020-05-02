package com.monstercode.campushub.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.monstercode.campushub.database.ItemDao
import com.monstercode.campushub.database.asDomainModel
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.network.NetworkItem
import com.monstercode.campushub.network.NetworkPicture
import com.monstercode.campushub.network.asDatabaseModel
import com.monstercode.campushub.network.getNetworkService
import com.monstercode.campushub.util.RefreshError
import timber.log.Timber

/**
 * Repository for fetching data from network and room.
 */

class ItemRepository(private val itemDao: ItemDao) {
    val itemList: LiveData<List<Item>> = Transformations.map(itemDao.getItems()) {
        it.asDomainModel()
    }

    fun getItem(itemId: String) = Transformations.map(itemDao.getItem(itemId)) {
        it.asDomainModel()
    }

    suspend fun refreshItems() {
        try {
            val items: List<NetworkItem> = getNetworkService().fetchItems()
            itemDao.insertAll(items.asDatabaseModel())

            val pictures: List<NetworkPicture> = getNetworkService().fetchPictures()
            Timber.i(pictures.toString())
            itemDao.insertPictures(pictures.asDatabaseModel())

        } catch (cause: Throwable) {
            Timber.e("Unable to refresh: $cause")
            throw RefreshError("Unable to fetch", cause)
        }
    }


}


