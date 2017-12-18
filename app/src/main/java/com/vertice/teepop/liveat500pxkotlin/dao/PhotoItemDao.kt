package com.vertice.teepop.liveat500pxkotlin.dao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by VerDev06 on 12/18/2017.
 */
data class PhotoItemDao(@SerializedName("id")
                        var id: Int = 0,
                        @SerializedName("link")
                        var link: String? = null,
                        @SerializedName("image_url")
                        var imageUrl: String? = null,
                        @SerializedName("caption")
                        var caption: String? = null,
                        @SerializedName("user_id")
                        var userId: Int = 0,
                        @SerializedName("username")
                        var username: String? = null,
                        @SerializedName("profile_picture")
                        var profilePicture: String? = null,
                        @SerializedName("tags")
                        var tags: List<String> = ArrayList(),
                        @SerializedName("created_time")
                        var createdTime: Date? = null,
                        @SerializedName("camera")
                        var camera: String? = null,
                        @SerializedName("lens")
                        var lens: String? = null,
                        @SerializedName("focal_length")
                        var focalLength: String? = null,
                        @SerializedName("iso")
                        var iso: String? = null,
                        @SerializedName("shutter_speed")
                        var shutterSpeed: String? = null,
                        @SerializedName("aperture")
                        var aperture: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        link = parcel.readString()
        imageUrl = parcel.readString()
        caption = parcel.readString()
        userId = parcel.readInt()
        username = parcel.readString()
        profilePicture = parcel.readString()
        tags = parcel.createStringArrayList()
        camera = parcel.readString()
        lens = parcel.readString()
        focalLength = parcel.readString()
        iso = parcel.readString()
        shutterSpeed = parcel.readString()
        aperture = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(link)
        parcel.writeString(imageUrl)
        parcel.writeString(caption)
        parcel.writeInt(userId)
        parcel.writeString(username)
        parcel.writeString(profilePicture)
        parcel.writeStringList(tags)
        parcel.writeString(camera)
        parcel.writeString(lens)
        parcel.writeString(focalLength)
        parcel.writeString(iso)
        parcel.writeString(shutterSpeed)
        parcel.writeString(aperture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemDao> {
        override fun createFromParcel(parcel: Parcel): PhotoItemDao {
            return PhotoItemDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemDao?> {
            return arrayOfNulls(size)
        }
    }
}