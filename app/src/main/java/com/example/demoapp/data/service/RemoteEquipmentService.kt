package com.example.demoapp.data.service

import com.example.demoapp.data.local.dao.EquipmentDetails
import com.example.demoapp.data.model.Equipment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteEquipmentService {

    @GET("/equipment/list")
    suspend fun list(): Response<List<Equipment>>

    @GET("/equipment/details/{id}")
    suspend fun details(@Path("id") id: Long): Response<EquipmentDetails>

}