package com.masaibar.retrofitokhttpsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button_get).setOnClickListener { get() }
    }

    private fun get() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<GithubService>(GithubService::class.java)

        val userCall = service.getUserInfo("masaibar")

        userCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("!!!", user!!.toString())
                } else {
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("!!!", "onFailure")
            }
        })
    }
}
