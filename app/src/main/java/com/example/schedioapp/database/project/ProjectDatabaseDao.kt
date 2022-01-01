package com.example.schedioapp.database.project

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

// https://developer.android.com/reference/kotlin/androidx/room/OnConflictStrategy

@Dao
interface ProjectDatabaseDao {

    // Insert all projects
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProjects(vararg projects: DatabaseProject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: DatabaseProject)

    // Insert all taken for all projects
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTaken(vararg taken: DatabaseTaak)*/

    // Fetch all projects, including taken
    //@Transaction
    @Query("SELECT * FROM project_table ORDER BY id")
    fun getAllProjects(): /*Array<DatabaseProjectWithTaken>*/List<DatabaseProject>

    @Query("SELECT * FROM project_table ORDER BY id DESC")
    fun getAllProjectsLive(): LiveData<List<DatabaseProject>>

    // Insert a specific project, including taken
    //@Transaction
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: DatabaseProject, taken: Array<DatabaseTaak>)*/

    //@Transaction
    /*@Query("SELECT * FROM project_table WHERE id = :key")
    suspend fun get(key: Int): Array<DatabaseProjectWithTaken>*/

    // Delete a specific project, including taken (remember onDelete = CASCADE)
    @Query("DELETE FROM project_table WHERE id = :key")
    suspend fun clear(key: Int)

}
