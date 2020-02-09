package com.example.airapi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeAdapter: HomeAdapter
    lateinit var homeMenager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView

    var airs: MutableList<Model.Stations> = arrayListOf()
    var favorites: MutableList<Int> = arrayListOf()
    var templist: MutableList<Model.Stations> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retrofit = Retrofit.Builder().baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(ApiService::class.java)

        val database = Repository(requireParentFragment())
        homeViewModelFactory = HomeViewModelFactory(database)
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_station, container, false)
        homeMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeAdapter = HomeAdapter(airs) {

        }
        recyclerView = root.findViewById(R.id.recyclerStation) as RecyclerView
        recyclerView.layoutManager = homeMenager
        recyclerView.adapter = homeAdapter

        favorites = homeViewModel.getCurrentUserFavorites().toMutableList()

        api.fetchAllStation().enqueue(object : Callback<List<Model.Stations>> {
            override fun onFailure(call: Call<List<Model.Stations>>, t: Throwable) {
                Log.e("blad", "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<List<Model.Stations>>,
                response: Response<List<Model.Stations>>
            ) {

                airs = response.body()!!.toMutableList()
                homeAdapter = HomeAdapter(airs) {


                }

                if (homeViewModel.getCurrentUserFavorites().toMutableList().size > 0) {
                    for (i in 0 until airs.size step 1) {
                        if (favorites.contains(airs[i].id)) {
                            //leave on list

                        } else {
                            //add to temporary list to delete later
                            templist.add(airs[i])

                        }
                    }


                    airs.removeAll(templist)
                    homeAdapter.notifyDataSetChanged()

                    recyclerView.adapter = homeAdapter
                }

            }
        }
        )

        return root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(name: String?) {
        homeViewModel.deleteFavouriteStation(name!!.toInt(), homeViewModel.getCurrentUserId())
        airs.clear()
        favorites.clear()

        favorites = homeViewModel.getCurrentUserFavorites().toMutableList()

        val retrofit = Retrofit.Builder().baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(ApiService::class.java)

        api.fetchAllStation().enqueue(object : Callback<List<Model.Stations>> {
            override fun onFailure(call: Call<List<Model.Stations>>, t: Throwable) {
                Log.e("blad", "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<List<Model.Stations>>,
                response: Response<List<Model.Stations>>
            ) {

                airs = response.body()!!.toMutableList()
                homeAdapter = HomeAdapter(airs) {


                }

                if (homeViewModel.getCurrentUserFavorites().toMutableList().size > 0) {
                    for (i in 0 until airs.size step 1) {
                        if (favorites.contains(airs[i].id)) {
                            //leave on list

                        } else {
                            //add to temporary list to delete later
                            templist.add(airs[i])

                        }
                    }

                    airs.removeAll(templist)
                    homeAdapter.notifyDataSetChanged()

                    recyclerView.adapter = homeAdapter

                    homeAdapter.notifyDataSetChanged()

                }
            }
        })
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