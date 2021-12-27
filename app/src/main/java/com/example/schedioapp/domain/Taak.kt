package com.example.schedioapp.domain

import java.util.*

data class Taak(
    var naam: String,
    var taakStartDatum: Date,
    var taakEindDatum: Date,
    var categorie: String,
    var prioriteit: String,
    var status: String
) {

}
