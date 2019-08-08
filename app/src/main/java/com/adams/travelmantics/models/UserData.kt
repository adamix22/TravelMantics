package com.adams.travelmantics.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
 class UserData(val email : String, val name : String) : Parcelable {

  constructor() : this("","")

}