package com.vertice.teepop.liveat500pxkotlin.dependency

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/18/2017.
 */

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun provideApplication() = application

    @Named("PRIMARY_PREFS")
    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences =
            application.getSharedPreferences("photo", Context.MODE_PRIVATE)
}