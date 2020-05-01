package com.monstercode.campushub.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val localUrl = "http://10.0.2.2:3000"
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
