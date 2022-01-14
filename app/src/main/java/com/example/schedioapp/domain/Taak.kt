package com.example.schedioapp.domain

import java.util.*

/**
 * Taak model class, which is not yet in use
 */
data class Taak(
    var taakId: Int,
    var naam: String,
    var taakStartDatum: Date,
    var taakEindDatum: Date,
    var categorie: String,
    var prioriteit: String,
    var status: String
)