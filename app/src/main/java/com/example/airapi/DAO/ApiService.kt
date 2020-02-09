package com.example.airapi.DAO

import com.example.airapi.models.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("station/findAll")
    fun fetchAllStation(): Call<List<Model.Stations>>

    @GET("station/sensors/{id}")
    fun fetchAllSensors(@Path("id") id: Int): Call<List<Model.Sensors>>
}