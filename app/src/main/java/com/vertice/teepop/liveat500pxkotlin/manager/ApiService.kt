package com.vertice.teepop.liveat500pxkotlin.manager

import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemCollectionDao
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Path

import javax.inject.Inject

/**
 * Created by VerDev06 on 12/18/2017.
 */
interface ApiService {

    companion object {
        //TODO: Error:org.jetbrains.kotlin.kapt3.diagnostic.KaptError: Error while annotation processing
        @Inject
        lateinit var retrofit: Retrofit

        fun create(): ApiService =
                retrofit.create(ApiService::class.java)
    }

    @POST("list")
    fun loadPhotoList(): Observable<PhotoItemCollectionDao>

    @POST("list/after/{id}")
    fun loadPhotoListAfterId(@Path("id") id: Int): Observable<PhotoItemCollectionDao>

    @POST("list/before/{id}")
    fun loadPhotoListBeforeId(@Path("id") id: Int): Observable<PhotoItemCollectionDao>
}