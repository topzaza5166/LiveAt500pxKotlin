package com.vertice.teepop.liveat500pxkotlin.adapter

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.ProgressBar
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemCollectionDao
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.utils.MutableInteger
import com.vertice.teepop.liveat500pxkotlin.view.PhotoListItem

/**
 * Created by VerDev06 on 12/19/2017.
 */
class PhotoListAdapter(var lastPositionInteger: MutableInteger = MutableInteger())
    : BaseAdapter() {

    var dao: PhotoItemCollectionDao? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (position == count - 1) {
            //Progress Bar
            return convertView as? ProgressBar ?: ProgressBar(parent?.context)
        }

        val dao: PhotoItemDao? = getItem(position) as? PhotoItemDao

        val item = convertView as? PhotoListItem ?: PhotoListItem(parent?.context!!)
        item.setNameText(dao?.caption)
        item.setDescriptionText(dao?.username + "\n" + dao?.camera)
        item.setImageUrl(dao?.imageUrl)


        if (position > lastPositionInteger.value) {
            val anim = AnimationUtils.loadAnimation(parent?.context, R.anim.up_from_bottom)
            item.startAnimation(anim)
            lastPositionInteger.value = position
        }

        return item
    }

    override fun getItem(p0: Int): Any {
        return dao?.data?.get(p0) ?: Any()
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dao?.data?.size ?: 0
    }

    fun increaseLastPosition(amount: Int) {
        lastPositionInteger.value += amount
    }
}