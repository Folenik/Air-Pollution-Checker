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
import android.widget.Toast
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

    var sensors: List<Model.Sensors> = emptyList()

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

        val retrofit = Retrofit.Builder().baseUrl("http://api.gios.gov.pl/pjp-api/rest/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(ApiService::class.java)

        viewMenager = LinearLayoutManager(this)
        viewAdapter = SensorAdapter(sensors) {

        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerSensor) as RecyclerView
        recyclerView.layoutManager = viewMenager
        recyclerView.adapter = viewAdapter

        val sensorId = intent.getIntExtra(this.getString(R.string.main_id), -1)

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
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val myIntent = Intent(applicationContext, MainActivity::class.java)
        startActivityForResult(myIntent, 0)
        return true
    }

}
