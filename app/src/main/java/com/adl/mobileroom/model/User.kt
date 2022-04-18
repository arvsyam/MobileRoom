package com.adl.mobileroom.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
class User (
    @PrimaryKey(autoGenerate = true) val id:Long? =0,
    val name: String,
    val gender:String,
    val age:String,
    val status:String
    ):Parcelable

