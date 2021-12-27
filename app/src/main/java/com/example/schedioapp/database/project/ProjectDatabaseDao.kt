package com.example.schedioapp.database.project

import android.provider.ContactsContract
import androidx.room.*

// https://developer.android.com/reference/kotlin/androidx/room/OnConflictStrategy

@Dao
interface ProjectDatabaseDao {

    // Insert all projects
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProjects(vararg projects: DatabaseProject)

    // Insert all taken for all projects
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTaken(vararg taken: DatabaseTaak)

    // Fetch all projects, including taken
    @Transaction
    @Query("SELECT * FROM project_table ORDER BY id")
    fun getAllProjects(): Array<DatabaseProjectWithTaken>

    // Insert a specific project, including taken
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: DatabaseProject, taken: Array<DatabaseTaak>)

    @Transaction
    @Query("SELECT * FROM project_table WHERE id = :key")
    suspend fun get(key: Int): Array<DatabaseProjectWithTaken>

    // Delete a specific project, including taken (remember onDelete = CASCADE)
    @Query("DELETE FROM project_table WHERE id = :key")
    suspend fun clear(key: Int)

}