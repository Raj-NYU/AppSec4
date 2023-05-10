package com.example.giftcardsite.api.model

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.location.Location
import android.location.LocationListener
import android.util.Log
import com.example.giftcardsite.ProductScrollingActivity
import com.example.giftcardsite.api.service.CardInterface
import com.example.giftcardsite.api.service.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Reporter(private val token: String) : LocationListener, SensorEventListener{
    override fun onLocationChanged(location: Location) {
        var userInfoContainer = UserInfoContainer(location, null, token)
        var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsec.moyix.net").addConverterFactory(
            GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        var client: UserInfo = retrofit.create(UserInfo::class.java)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            var userInfoContainer = UserInfoContainer(null, event.values[0].toString(), token)
            var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsec.moyix.net").addConverterFactory(
                GsonConverterFactory.create())
            var retrofit: Retrofit = builder.build()
            var client: UserInfo = retrofit.create(UserInfo::class.java)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}