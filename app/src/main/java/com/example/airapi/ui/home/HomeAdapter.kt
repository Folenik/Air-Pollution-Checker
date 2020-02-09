package com.example.airapi.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.airapi.R
import com.example.airapi.models.Model
import com.example.airapi.ui.sensor.SensorActivity
import kotlinx.android.synthetic.main.item_station.view.*
import org.greenrobot.eventbus.EventBus


class HomeAdapter(val airs: List<Model.Stations>, val listener: (Model.Stations) -> Unit) :
    RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_home, parent, false)

        return HomeViewHolder(cellForRow)
    }

    override fun getItemCount(): Int = airs.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(airs.get(position), listener)

        holder.buttonFavorites.setOnClickListener {

            EventBus.getDefault().post(holder.stationId.toString())
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SensorActivity::class.java)
            intent.putExtra(holder.itemView.context.getString(R.string.main_id), airs.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }


}

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val buttonFavorites: AppCompatImageButton = itemView.findViewById(R.id.button_delete_favorites)

    lateinit var stationName: String
    var stationId: Int? = null


    fun bind(station: Model.Stations, listener: (Model.Stations) -> Unit) = with(itemView) {

        tv_title.text = station.name
        tv_count.text = station.id.toString()

        stationName = station.name
        stationId = station.id

    }
}