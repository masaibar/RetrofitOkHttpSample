package com.masaibar.retrofitokhttpsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val USER = "masaibar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button_get).setOnClickListener { get() }
    }

    private fun get() {
        getAsObservable()
        getAsCall()
    }

    //http://sssslide.com/speakerdeck.com/shige0501/25-rxjava2-plus-okhttp-plus-retrofitru-men あと一歩？                .
    fun getAsObservable() {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<GithubService>(GithubService::class.java)
        val userObservable = service.getUserInfoAsObservable(USER)
        userObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun getAsCall() {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<GithubService>(GithubService::class.java)
        val userCall = service.getUserInfoAsCall(USER)

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
