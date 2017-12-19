package com.vertice.teepop.liveat500pxkotlin.manager

import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemCollectionDao
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by VerDev06 on 12/18/2017.
 */
interface ApiService {

//    companion object {
//        fun create(): ApiService {
//
//            val retrofit = Retrofit.Builder()
//                    .addCallAdapterFactory(
//                            RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(
//                            GsonConverterFactory.create())
//                    .baseUrl("https://en.wikipedia.org/w/")
//                    .build()
//
//            return retrofit.create(ApiService::class.java)
//        }
//    }

    @POST("list")
    fun loadPhotoList(): Observable<PhotoItemCollectionDao>

    @POST("list/after/{id}")
    fun loadPhotoListAfterId(@Path("id") id: Int): Observable<PhotoItemCollectionDao>

    @POST("list/before/{id}")
    fun loadPhotoListBeforeId(@Path("id") id: Int): Observable<PhotoItemCollectionDao>
}