package com.example.schedioapp.database.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.schedioapp.domain.Project
import com.squareup.moshi.Json

/**
 * Database entity DatabaseProject
 * This represents a Project in the database
 */
@Entity(tableName = "project_table")
data class DatabaseProject(
        @PrimaryKey()
        @ColumnInfo(name = "id")
        @Json(name = "id")
        val id: Int = 0,

        @ColumnInfo(name = "project_naam")
        @Json(name = "naam")
        val naam: String,

        @ColumnInfo(name = "project_start_datum")
        @Json(name = "startDatum")
        val startDatum: String,

        @ColumnInfo(name = "project_eind_datum")
        @Json(name = "eindDatum")
        val eindDatum: String,

        @ColumnInfo(name = "project_budget")
        @Json(name = "budget")
        val budget: Double,

        @ColumnInfo(name = "project_status")
        @Json(name = "status")
        val status: String,

        @ColumnInfo(name = "project_type")
        @Json(name = "type")
        val type: String
)
/*
@Entity(tableName = "taak_table",
        foreignKeys = arrayOf(
        ForeignKey(
                entity = DatabaseProject::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("project_id"),
                onDelete = CASCADE,
                onUpdate = CASCADE,
                deferred = false)
        ),
        indices = arrayOf(
                Index(value = arrayOf("project_id"))
        )
)
data class DatabaseTaak(

        @PrimaryKey()
        @ColumnInfo(name = "taak_id")
        val taakId: Int,

        @ColumnInfo(name = "taak_naam")
        @Json(name = "naam")
        val naam: String,

        @ColumnInfo(name = "taak_start_datum")
        @Json(name = "taakStartDatum")
        val taakStartDatum: String,

        @ColumnInfo(name = "taak_eind_datum")
        @Json(name = "taakEindDatum")
        val taakEindDatum: String,

        @ColumnInfo(name = "taak_categorie")
        @Json(name = "categorie")
        val categorie: String,

        @ColumnInfo(name = "taak_prioriteit")
        @Json(name = "prioriteit")
        val prioriteit: String,

        @ColumnInfo(name = "taak_status")
        @Json(name = "status")
        val status: String,

        @ColumnInfo(name = "project_id")
        val projectId: Int
)

data class DatabaseProjectWithTaken(
        @Embedded
        val project: DatabaseProject,
        @Relation(
                parentColumn = "id",
                entityColumn = "project_id"
        )
        val taken: List<DatabaseTaak>
)
*/

// Convert Project to ApiProject
fun List<DatabaseProject/*WithTaken*/>.asDomainModel(): List<Project> {
        val dateConverter: DateConverter = DateConverter()
        return map {
                Project(
                        id = it/*.project*/.id,
                        naam = it/*.project*/.naam,
                        startDatum = dateConverter.toDate(it/*.project*/.startDatum),
                        eindDatum = dateConverter.toDate(it/*.project*/.startDatum),
                        budget = it/*.project*/.budget,
                        status = it/*.project*/.status,
                        type = it/*.project*/.type
                        /*taken = it.taken.map { databaseTaak ->
                                Taak(
                                        databaseTaak.taakId,
                                        databaseTaak.naam,
                                        dateConverter.toDate(databaseTaak.taakStartDatum),
                                        dateConverter.toDate(databaseTaak.taakEindDatum),
                                        databaseTaak.categorie,
                                        databaseTaak.prioriteit,
                                        databaseTaak.status
                                )
                        }*/
                )
        }
}
