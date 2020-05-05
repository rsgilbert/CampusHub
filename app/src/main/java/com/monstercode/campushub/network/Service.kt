package com.monstercode.campushub.network

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val localUrl = "http://10.0.2.2:5000"
private const val baseUrl = "https://bigmotherhen.herokuapp.com"
private const val inUseUrl = localUrl

/**
 * A retrofit service to fetch data
 */
interface Service {
    @GET("items")
    suspend fun fetchItems(): List<NetworkItem>

    @GET("orders")
    suspend fun fetchOrders(): List<NetworkOrder>

    @GET("pictures")
    suspend fun fetchPictures(): List<NetworkPicture>

    @FormUrlEncoded
    @POST("items")
    suspend fun postNewItem(
        @Field("name")
        name: String,
        @Field("price")
        price: Int
    ): NetworkItem


    @DELETE("items/{itemId}")
    suspend fun deleteItem(@Path("itemId") itemId: String): NetworkItem


    @FormUrlEncoded
    @PUT("items/{itemId}")
    suspend fun updateName(
        @Path("itemId") itemId: String,
        @Field("name") name: String
    ): NetworkItem

    @FormUrlEncoded
    @PUT("items/{itemId}")
    suspend fun updatePrice(
        @Path("itemId") itemId: String,
        @Field("price") price: Int
    ): NetworkItem


    @Multipart
    @POST("pictures")
    suspend fun uploadPicture(
        @Part part: MultipartBody.Part
    ): NetworkPicture

    @DELETE("pictures/{pictureId}")
    suspend fun deletePicture(@Path("pictureId") pictureId: String): NetworkPicture

}

/**
 * Main entry point for network access.
 */
private val service: Service by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(inUseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(Service::class.java)
}

fun getNetworkService() = service
