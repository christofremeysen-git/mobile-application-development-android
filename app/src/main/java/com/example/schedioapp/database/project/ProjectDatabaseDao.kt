package com.example.schedioapp.database.project

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * This interface contains database functions
 */
@Dao
interface ProjectDatabaseDao {

    /**
     * Inserts all projects into the database
     *
     * @property projects A number of database projects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProjects(vararg projects: DatabaseProject)

    /**
     * Inserts all projects into the database
     *
     * @property projects A list of database projects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProjects(projects: List<DatabaseProject>)

    /**
     * Inserts a project into the database
     *
     * @property project A database project
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: DatabaseProject)

    /**
     * Fetches all projects
     *
     * @return A list of database projects
     */
    @Query("SELECT * FROM project_table ORDER BY id")
    fun getAllProjects(): List<DatabaseProject>

    /**
     * Fetches a project by means of the project id
     *
     * @property key The project id
     * @return A database project
     */
    @Query("SELECT * FROM project_table WHERE id = :key")
    fun getProject(key: Int): DatabaseProject

    /**
     * Fetches all projects, taking state into account
     *
     * @return A list of LiveData database projects
     */
    @Query("SELECT * FROM project_table ORDER BY project_naam ASC")
    fun getAllProjectsLive(): LiveData<List<DatabaseProject>>

    /**
     * Filters projects according to project status
     *
     * @property key The project status
     * @return A list of Live Data database projects
     */
    @Query("SELECT * FROM project_table WHERE project_status = :key ORDER BY id")
    fun getAllFilteredProjects(key: String): LiveData<List<DatabaseProject>>

    /**
     * Deletes all database projects
     */
    @Query("DELETE FROM project_table")
    fun clear()

    /**
     * Deletes a database project
     *
     * @property project A database project
     */
    @Delete
    suspend fun delete(project: DatabaseProject)

}
