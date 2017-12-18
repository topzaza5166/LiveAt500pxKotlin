package com.vertice.teepop.liveat500pxkotlin.dependency

import com.vertice.teepop.liveat500pxkotlin.fragment.MainFragment
import com.vertice.teepop.liveat500pxkotlin.manager.ApiService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/18/2017.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetModule::class
))
interface AppComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(apiService: ApiService)
}