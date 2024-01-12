package com.example.customar.models

import java.util.Date
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("itemId")
    val itemId: String,
    @SerializedName("itemName")
    val itemName: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("offerPrice")
    val offerPrice: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("images")
    val image: List<String>,
    @SerializedName("category")
    val category: String,
    @SerializedName("newArrival")
    val isNewArrival: Date,
    @SerializedName("rating")
    val rating: Number,
    @SerializedName("isWishlisted")
    val isWishlisted: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte() // Read isWishlisted as Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemId)
        parcel.writeString(itemName)
        parcel.writeString(price)
        parcel.writeString(offerPrice)
        parcel.writeString(about)
        parcel.writeStringList(image)
        parcel.writeString(category)
        parcel.writeLong(isNewArrival.time)
        parcel.writeDouble(rating.toDouble())
        parcel.writeByte(if (isWishlisted) 1 else 0) // Write isWishlisted as Byte
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
