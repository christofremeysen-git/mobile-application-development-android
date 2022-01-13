package com.example.schedioapp.database.project

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProjectDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProjects(vararg projects: DatabaseProject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: DatabaseProject)

    @Query("SELECT * FROM project_table ORDER BY id")
    fun getAllProjects(): List<DatabaseProject>

    @Query("SELECT * FROM project_table ORDER BY id DESC")
    fun getAllProjectsLive(): LiveData<List<DatabaseProject>>

    @Query("SELECT * FROM project_table WHERE project_status = :key ORDER BY id")
    fun getAllFilteredProjects(key: String): LiveData<List<DatabaseProject>>

    @Query("DELETE FROM project_table WHERE id = :key")
    fun clear(key: Int)

    @Delete
    suspend fun delete(project: DatabaseProject)

}
