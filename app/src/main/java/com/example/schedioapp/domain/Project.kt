package com.example.schedioapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Project (
    var id: Int,
    
    val naam: String,
    val startDatum: Date,
    val eindDatum: Date,
    val budget: Double,
    val status: String,
    val type: String
    // val taken: List<Taak>
) : Parcelable
