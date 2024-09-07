package com.example.demoapp.data.service

import com.example.demoapp.data.model.InventoryItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteInventoryService {

    @GET("/inventory/item/{qr}")
    suspend fun itemDetails(@Path("qr") id: String): Response<InventoryItem>

    @POST("/inventory/send")
    suspend fun sendInventory(@Body items: List<InventoryItem>): Response<String>
}