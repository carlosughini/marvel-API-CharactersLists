package com.carlosughini.appmarvel.service

import com.carlosughini.appmarvel.extensions.md5
import com.carlosughini.appmarvel.models.entity.PRIVATE_KEY
import com.carlosughini.appmarvel.models.entity.PUBLIC_KEY
import com.carlosughini.appmarvel.models.entity.Response
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface MarvelApi {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 0): Observable<Response>


    @GET("comics")
    fun allComics(@Query("offset") offset: Int? = 0): Observable<Response>

    companion object {
        fun getService(): MarvelApi {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", PUBLIC_KEY)
                        .addQueryParameter("ts", ts)
                        .addQueryParameter("hash", "$ts$PRIVATE_KEY$PUBLIC_KEY".md5())
                        .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create<MarvelApi>(MarvelApi::class.java)
        }
    }
}