package com.vertice.teepop.liveat500pxkotlin.dependency

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vertice.teepop.liveat500pxkotlin.manager.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/18/2017.
 */

@Module
class NetModule(var baseUrl: String) {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

}