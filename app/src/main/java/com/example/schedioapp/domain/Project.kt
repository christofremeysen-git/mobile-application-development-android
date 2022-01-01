package com.example.schedioapp.domain

import java.util.*

data class Project(
    var id: Int = 0,
    val naam: String,
    val startDatum: Date,
    val eindDatum: Date,
    val type: String,
    val budget: Number,
    val status: String,
    // val taken: List<Taak>
) {

}
