package com.vertice.teepop.liveat500pxkotlin.manager

import android.content.SharedPreferences
import android.os.Bundle
import com.google.gson.Gson
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemCollectionDao

/**
 * Created by VerDev06 on 12/19/2017.
 */
class PhotoListManager(var prefs: SharedPreferences) {

    var dao: PhotoItemCollectionDao = PhotoItemCollectionDao()

    init {
        loadCache()
    }

    fun insertDaoAtTopPosition(newDao: PhotoItemCollectionDao) {
        dao.data.addAll(0, newDao.data)
        saveCache()
    }

    fun appendDaoAtBottomPosition(newDao: PhotoItemCollectionDao) {
        dao.data.addAll(newDao.data)
        saveCache()
    }

    fun getMaximumId(): Int {
        var maxId = dao.data[0].id
        for (i in 1 until dao.data.size)
            maxId = Math.max(maxId, dao.data[i].id)

        return maxId
    }

    fun getMinimumId(): Int {
        var minId = dao.data[0].id
        for (i in 1 until dao.data.size)
            minId = Math.min(minId, dao.data[i].id)

        return minId
    }

    fun getCount(): Int {
        return dao.data.size
    }

    fun onSaveInstanceState(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable("dao", dao)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        dao = savedInstanceState.getParcelable("dao")
    }

    private fun saveCache() {
        val cacheDao = PhotoItemCollectionDao()
        cacheDao.data = dao.data.subList(0, Math.min(20, dao.data.size))

        val json = Gson().toJson(cacheDao)

        prefs.edit()
                .putString("json", json)
                .apply()

    }

    private fun loadCache() {
        val json: String = prefs.getString("json", null) ?: return
        dao = Gson().fromJson(json, PhotoItemCollectionDao::class.java)
    }
}
