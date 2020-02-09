package com.example.airapi.models

import com.google.gson.annotations.SerializedName


object Model {
    data class Stations(@SerializedName("stationName") val name: String, @SerializedName("id") val id: Int)
    data class Sensors(@SerializedName("stationId") val id: Int, val param: Param)
    data class Param(@SerializedName("paramName") val paramName: String, @SerializedName("paramCode") val paramCode: String)
}