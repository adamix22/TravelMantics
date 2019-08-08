package com.adams.travelmantics.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserTravels(val region : String, val price: String, val place : String, val image : String ) : Parcelable  {
    constructor() : this("","","","")
}