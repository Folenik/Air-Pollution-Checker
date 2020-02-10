package com.example.airapi.ui.sensor

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airapi.DAO.ApiService
import com.example.airapi.MainActivity
import com.example.airapi.R
import com.example.airapi.models.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SensorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewMenager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: SensorAdapter

    private lateinit var recyclerView2: RecyclerView
    private lateinit var viewMenager2: RecyclerView.LayoutManager
    private lateinit var viewAdapter2: SensorValueAdapter

    val retrofit = Retrofit.Builder().baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val api = retrofit.create(ApiService::class.java)

    val arrayOfParams = IntArray(size = 10)
    var arrayWhichHoldsAllParameters: MutableList<String> = arrayListOf()

    var sensors: List<Model.Sensors> = emptyList()
    lateinit var sensorData: Model.SensorData


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        val mToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(mToolbar)
        mToolbar.setTitleTextColor(resources.getColor(R.color.white))
        mToolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        val upArrow: Drawable = getResources().getDrawable(R.drawable.ic_arrow_back_24dp)
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        getSupportActionBar()!!.setHomeAsUpIndicator(upArrow);
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setTitle("Sensors")


        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))

        viewMenager = LinearLayoutManager(this)
        viewAdapter = SensorAdapter(sensors) {

        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerSensor) as RecyclerView
        recyclerView.layoutManager = viewMenager
        recyclerView.adapter = viewAdapter

        viewMenager2 = LinearLayoutManager(this)
        recyclerView2 = findViewById<RecyclerView>(R.id.recyclerSensorValue) as RecyclerView
        recyclerView2.layoutManager = viewMenager2


        val sensorId = intent.getIntExtra(this.getString(R.string.main_id), -1)
        Log.i("sensorId", sensorId.toString())

        api.fetchAllSensors(sensorId).enqueue(object : Callback<List<Model.Sensors>> {
            override fun onFailure(call: Call<List<Model.Sensors>>, t: Throwable) {
                Log.e("blad", "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<List<Model.Sensors>>,
                response: Response<List<Model.Sensors>>
            ) {
                sensors = response.body()!!
                viewAdapter = SensorAdapter(sensors) {
                }
                recyclerView.adapter = viewAdapter
                for (i in 0 until sensors.size step 1) {
                    arrayOfParams[i] = viewAdapter.sensors.get(i).sensorId
                }

                fetchSensorData(sensors.size)
            }
        })


    }

    fun fetchSensorData(howMuch: Int) {
        for (i in 0 until howMuch step 1) {
            api.fetchSensorData(arrayOfParams[i]).enqueue(object : Callback<Model.SensorData> {
                override fun onFailure(call: Call<Model.SensorData>, t: Throwable) {
                    Log.e("blad", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<Model.SensorData>,
                    response: Response<Model.SensorData>

                ) {
                    sensorData = response.body()!!
                    try {
                        arrayWhichHoldsAllParameters.add(sensorData.values.get(0).value.toString())
                        viewAdapter2 = SensorValueAdapter(arrayWhichHoldsAllParameters) {
                        }
                        recyclerView2.adapter = viewAdapter2
                    } catch (e: NullPointerException) {
                        Log.i("NPE caught", e.toString())
                        try {

                            arrayWhichHoldsAllParameters.add(sensorData.values.get(1).value.toString())
                            viewAdapter2 = SensorValueAdapter(arrayWhichHoldsAllParameters) {
                            }
                            recyclerView2.adapter = viewAdapter2
                        } catch (e: NullPointerException) {
                            Log.i("NPE caught", e.toString())
                        }
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val myIntent = Intent(applicationContext, MainActivity::class.java)
        startActivityForResult(myIntent, 0)
        return true
    }

}
