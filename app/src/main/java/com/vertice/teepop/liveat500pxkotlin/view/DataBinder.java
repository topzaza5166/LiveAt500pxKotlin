package com.vertice.teepop.liveat500pxkotlin.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vertice.teepop.liveat500pxkotlin.dependency.GlideApp;

/**
 * Created by topza on 12/23/2017.
 */

/**
 * ฺBindingAdapter require public static method but kotlin can't define
 */
public class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context)
                .load(url)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
