package com.vertice.teepop.liveat500pxkotlin.view

import android.annotation.TargetApi
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.databinding.ListItemPhotoBinding
import com.vertice.teepop.liveat500pxkotlin.view.state.BundleSavedState


/**
 * Created by nuuneoi on 11/16/2014.
 */
class PhotoListItem : BaseCustomViewGroup {

    lateinit var binding: ListItemPhotoBinding

    constructor(context: Context) : super(context) {
        initInflate()
        initInstances()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initInflate() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ListItemPhotoBinding.inflate(inflater, this, true)
    }

    private fun initInstances() {
        // findViewById here
    }

    private fun initWithAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return BundleSavedState(superState)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as BundleSavedState
        super.onRestoreInstanceState(ss.superState)

        val bundle = ss.bundle
        // Restore State from bundle here
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = width * 2 / 3
        val newHeightMeasureApec = MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY
        )
        //Child View
        super.onMeasure(widthMeasureSpec, newHeightMeasureApec)
        //Self
        setMeasuredDimension(width, height)
    }

    fun setDao(dao: PhotoItemDao?) {
        dao.let {
            binding.dao = it
        }
    }

    fun setNameText(name: String?) {
        binding.tvName.text = name
    }

    fun setDescriptionText(description: String?) {
        binding.tvDescription.text = description
    }

    fun getImageView(): ImageView {
        return binding.ivImg
    }

    fun setImageUrl(url: String?) {
        Glide.with(context)
                .load(url)
                .into(binding.ivImg)
    }

}
