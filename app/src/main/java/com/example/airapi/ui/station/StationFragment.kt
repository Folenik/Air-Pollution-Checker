package com.example.airapi.ui.station

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airapi.DAO.ApiService
import com.example.airapi.DAO.Repository
import com.example.airapi.R
import com.example.airapi.models.Model
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StationFragment : Fragment() {

    lateinit var stationViewModel: StationViewModel
    lateinit var stationViewModelFactory: StationViewModelFactory
    lateinit var stationAdapter: StationAdapter
    lateinit var stationMenager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView

    var airs: List<Model.Stations> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retrofit = Retrofit.Builder().baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(ApiService::class.java)

        val database = Repository(requireParentFragment())
        stationViewModelFactory = StationViewModelFactory(database)
        stationViewModel =
            ViewModelProviders.of(this, stationViewModelFactory).get(StationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_station, container, false)
        stationMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        stationAdapter = StationAdapter(airs) {

        }
        recyclerView = root.findViewById(R.id.recyclerStation) as RecyclerView
        recyclerView.layoutManager = stationMenager
        recyclerView.adapter = stationAdapter

        api.fetchAllStation().enqueue(object : Callback<List<Model.Stations>> {
            override fun onFailure(call: Call<List<Model.Stations>>, t: Throwable) {
                Log.e("blad", "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<List<Model.Stations>>,
                response: Response<List<Model.Stations>>
            ) {
                airs = response.body()!!
                stationAdapter = StationAdapter(airs) {

                }
                recyclerView.adapter = stationAdapter
            }
        })

        return root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(name: String?) {
        if (stationViewModel.checkIfUserLoggedIn() == true) {
            stationViewModel.setFavouriteStation(
                name!!.toInt(), stationViewModel.getCurrentUserId()
            )
        } else {
            Toast.makeText(context, getString(R.string.login_first), LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


}