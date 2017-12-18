package com.vertice.teepop.liveat500pxkotlin

import android.app.Application
import com.vertice.teepop.liveat500pxkotlin.dependency.AppComponent
import com.vertice.teepop.liveat500pxkotlin.dependency.AppModule
import com.vertice.teepop.liveat500pxkotlin.dependency.DaggerAppComponent
import com.vertice.teepop.liveat500pxkotlin.dependency.NetModule

/**
 * Created by VerDev06 on 12/18/2017.
 */
class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("http://nuuneoi.com/courses/500px/"))
                .build()
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}