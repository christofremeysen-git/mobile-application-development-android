package com.example.schedioapp.network

import android.os.Parcel
import android.os.Parcelable
import com.example.schedioapp.database.project.DatabaseProject
//import com.example.schedioapp.database.project.DatabaseTaak
import com.example.schedioapp.database.project.DateConverter
import com.example.schedioapp.domain.Project
//import com.example.schedioapp.domain.Taak
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import org.json.JSONArray
import java.lang.UnsupportedOperationException
import kotlinx.serialization.Serializable

@Serializable
data class ApiProjectContainer(
    //@Json(name="body")
    @Serializable(with = ProjectsSerializer::class)
    val apiProjects: ArrayList<ApiProject>
)

@Serializable(with = ProjectSerializer::class)
data class ApiProject(
    val id: Int,
    val naam: String,
    val startDatum: String,
    val eindDatum: String,
    val budget: Double,
    val status: String,
    val type: String
)

/*
fun ApiProjectContainer.asDomainModel(): List<Project> {
    val dateConverter = DateConverter()
    return apiProjects.map {
        Project(
            id = it.id.toInt(),
            naam = it.naam,
            startDatum = dateConverter.toDate(it.startDatum),
            eindDatum = dateConverter.toDate(it.eindDatum),
            budget = it.budget.toInt(),
            status = it.status,
            type = it.type,
            /*taken = it.taken.map {
                Taak(
                    taakId = it.taakId.toInt(),
                    naam = it.naam,
                    taakStartDatum = dateConverter.toDate(it.taakStartDatum),
                    taakEindDatum = dateConverter.toDate(it.taakEindDatum),
                    categorie = it.categorie,
                    prioriteit = it.prioriteit,
                    status = it.status
                )
            }*/
        )
    }
}
*/
fun ApiProjectContainer.asDatabaseProjectModel(): Array<DatabaseProject> {
    return apiProjects.map {
        DatabaseProject(
            id = it.id,
            naam = it.naam,
            startDatum = it.startDatum,
            eindDatum = it.eindDatum,
            budget = it.budget,//.toInt(),
            status = it.status,
            type = it.type
        )
    }.toTypedArray()
}

fun ApiProject.asDatabaseProject(): DatabaseProject {
    return DatabaseProject(
        id = id,
        naam = naam,
        startDatum = startDatum,
        eindDatum = eindDatum,
        budget = budget,//.toInt(),
        status = status,
        type = type
    )
}