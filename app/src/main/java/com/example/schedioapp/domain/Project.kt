package com.example.schedioapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Project model class, which is parcelable for transmission across fragments
 */
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
