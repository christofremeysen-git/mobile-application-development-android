package com.example.schedioapp.network

import com.example.schedioapp.database.project.DatabaseProject
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

/**
 * API model class, containing an arraylist of projects, serialized in a custom way
 */
@Serializable
data class ApiProjectContainer(
    @Serializable(with = ProjectsSerializer::class)
    val apiProjects: ArrayList<ApiProject>
)

/**
 * API model class, which is serialized in a custom way
 */
@Serializable(with = ProjectSerializer::class)
data class ApiProject(
    @Required
    val id: Int,
    @Required
    val naam: String,
    @Required
    val startDatum: String,
    @Required
    val eindDatum: String,
    @Required
    val budget: Double,
    @Required
    val status: String,
    @Required
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

/**
 * Method converting an array of API projects to an array of database projects
 * Returns an array of database projects
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

/**
 * Method converting an API project to a database project
 * @return Returns a database project
 */
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
