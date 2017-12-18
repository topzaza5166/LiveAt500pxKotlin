package com.vertice.teepop.liveat500pxkotlin.dao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by VerDev06 on 12/18/2017.
 */
data class PhotoItemCollectionDao(
        @SerializedName("success") var success: Boolean,
        @SerializedName("data") var data: List<PhotoItemDao>)
    : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(PhotoItemDao))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemCollectionDao> {
        override fun createFromParcel(parcel: Parcel): PhotoItemCollectionDao {
            return PhotoItemCollectionDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemCollectionDao?> {
            return arrayOfNulls(size)
        }
    }
}