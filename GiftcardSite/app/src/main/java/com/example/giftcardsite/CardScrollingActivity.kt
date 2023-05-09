package com.example.giftcardsite

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftcardsite.api.model.*
import com.example.giftcardsite.api.service.CardInterface
import com.example.giftcardsite.api.service.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class CardScrollingActivity : AppCompatActivity(), SensorEventListener, LocationListener {
    private var loggedInUser: User? = null
    private lateinit var sensorManager: SensorManager
    private var mAccel: Sensor? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        loggedInUser = intent.getParcelableExtra<User>("User")
        var button: Button = findViewById<Button>(R.id.view_cards_button)
        button.text = "View Products"
        button.setOnClickListener {
            val intent = Intent(this, ProductScrollingActivity::class.java).apply {
                putExtra("User", loggedInUser)
            }
            startActivity(intent)
        }
        var builder: Retrofit.Builder =
            Retrofit.Builder().baseUrl("https://appsec.moyix.net").addConverterFactory(
                GsonConverterFactory.create()
            )
        var retrofit: Retrofit = builder.build()
        var client: CardInterface = retrofit.create(CardInterface::class.java)
        val outerContext = this
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val token = "Token ${loggedInUser?.token}"
        recyclerView.layoutManager = manager
    }
}