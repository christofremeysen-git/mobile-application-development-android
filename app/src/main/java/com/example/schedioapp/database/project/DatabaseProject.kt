package com.example.schedioapp.database.project

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.squareup.moshi.Json

// https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1

@Entity(tableName = "project_table")
data class DatabaseProject(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
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

        @ColumnInfo(name = "project_type")
        @Json(name = "type")
        val type: String,

        @ColumnInfo(name = "project_budget")
        @Json(name = "budget")
        val budget: Int,

        @ColumnInfo(name = "project_status")
        @Json(name = "status")
        val status: String,
)

@Entity(tableName = "taak_table",
        foreignKeys = arrayOf(
        ForeignKey(
                entity = DatabaseProject::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("project_id"),
                onDelete = CASCADE,
                onUpdate = CASCADE,
                deferred = false)
        )
)
data class DatabaseTaak(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "taak_id")
        val taakId: Int,

        @ColumnInfo(name = "project_id")
        val projectId: Int,

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
        val status: String
)

data class DatabaseProjectWithTaken(
        @Embedded
        val project: DatabaseProject,
        @Relation(
                parentColumn = "id",
                entityColumn = "project_id"
        )
        val taken: List<DatabaseTaak> = ArrayList()
)