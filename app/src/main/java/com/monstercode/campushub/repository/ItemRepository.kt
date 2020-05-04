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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.InputStream

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

    suspend fun postNewItem(name: String, price: Int): Item? {
        try {
            val dbItem = getNetworkService().postNewItem(name, price).asDatabaseModel()
            itemDao.insertOneItem(dbItem)
            return dbItem.asDomainModel()
        } catch (e: Exception) {
            throw RefreshError("Error posting", e)
        }
    }

    suspend fun deleteItem(itemId: String): Boolean? {
        try {
            val dbItem = getNetworkService().deleteItem(itemId).asDatabaseModel()
            itemDao.deleteOne(dbItem._id)
            return true
        } catch (cause: Throwable) {
            throw RefreshError("Error deleting", cause)
        }
    }

    suspend fun uploadPicture(inputStream: InputStream, item: Item): Boolean? {
        val part: MultipartBody.Part = MultipartBody.Part.createFormData(
            "picture_image",
            item._id,
            RequestBody.create(
                MediaType.parse("image/*"),
                inputStream.readBytes()
            )
        )

        try {
            val networkPicture = getNetworkService().uploadPicture(part)
            itemDao.insertOnePicture(networkPicture.asDatabaseModel())
            return true
        } catch (cause: Throwable) {
            Timber.e(cause)
            return false
//            throw RefreshError("Error uploading $cause", cause)
        }
    }

    suspend fun refreshItems() {
        try {
            val items: List<NetworkItem> = getNetworkService().fetchItems()
            itemDao.insertAll(items.asDatabaseModel())

            val pictures: List<NetworkPicture> = getNetworkService().fetchPictures()
            itemDao.insertPictures(pictures.asDatabaseModel())
        } catch (cause: Throwable) {
            throw RefreshError("Unable to fetch", cause)
        }
    }


}


