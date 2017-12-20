package com.vertice.teepop.liveat500pxkotlin.utils

import android.os.Bundle

/**
 * Created by VerDev06 on 12/19/2017.
 */
class MutableInteger(var value: Int = 0) {

    fun onSaveInstanceState(): Bundle {
        val bundle = Bundle()
        bundle.putInt("value", value)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        value = savedInstanceState.getInt("value")
    }

}