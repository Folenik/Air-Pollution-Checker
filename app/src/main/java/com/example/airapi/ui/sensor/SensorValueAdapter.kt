package com.example.airapi.ui.sensor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.airapi.R
import com.example.airapi.models.Model
import kotlinx.android.synthetic.main.item_sensor.view.*
import kotlinx.android.synthetic.main.item_sensor_value.view.*


class SensorValueAdapter(val sensorsData: MutableList<String>, val listener: (Model.SensorData) -> Unit) :
    RecyclerView.Adapter<SensorValueAdapter.SensorValueViewHolder>() {

    class SensorValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sensorData: String, listener: (Model.SensorData) -> Unit) = with(itemView) {
            tv_params?.text = sensorData
            //Log.i("key1",sensorData.values.toString())
            //Log.i("key2",sensorData.values.get(1).value.toString())
            //tv_params?.text = sensor.sensorData.key.toString()
        }



    }

    override fun getItemCount(): Int {
        return sensorsData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorValueViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sensor_value, parent, false)
        return SensorValueViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SensorValueViewHolder, position: Int) {
        holder.bind(sensorsData.get(position),listener)
    }
}

