package com.vertice.teepop.liveat500pxkotlin.view

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by VerDev06 on 12/19/2017.
 */
class DataBinder {

    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .load(url)
                .into(imageView)
    }
}