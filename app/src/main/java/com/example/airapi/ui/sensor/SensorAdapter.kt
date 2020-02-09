package com.example.airapi.ui.sensor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.airapi.R
import com.example.airapi.models.Model
import kotlinx.android.synthetic.main.item_sensor.view.*


class SensorAdapter(val sensors: List<Model.Sensors>, val listener: (Model.Sensors) -> Unit) :
    RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sensor: Model.Sensors, listener: (Model.Sensors) -> Unit) = with(itemView) {
            tv_id?.text = sensor.param.paramName.toString()
            tv_params?.text = sensor.param.paramCode.toString()
        }
    }

    override fun getItemCount(): Int {
        return sensors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sensor, parent, false)
        return SensorViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        holder.bind(sensors.get(position), listener)
    }
}

