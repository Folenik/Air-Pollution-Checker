package com.example.airapi.models

import com.google.gson.annotations.SerializedName


object Model {
    data class Stations(@SerializedName("stationName") val name: String, @SerializedName("id") val id: Int)
    data class Sensors(@SerializedName("stationId") val id: Int, @SerializedName("id") val sensorId: Int, val param: Param)
    data class Param(@SerializedName("paramName") val paramName: String, @SerializedName("paramCode") val paramCode: String)
    data class SensorData(@SerializedName("key") val key: String, @SerializedName("values") val values: Array<Values>)
    data class Values(@SerializedName("value") val value: String, @SerializedName("date") val date: String)
}