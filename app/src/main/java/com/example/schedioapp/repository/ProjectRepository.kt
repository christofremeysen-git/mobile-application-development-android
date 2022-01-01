package com.example.schedioapp.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.asDomainModel
import com.example.schedioapp.domain.Project
import com.example.schedioapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import java.lang.Exception

class ProjectRepository(private val database: ProjectDatabase) {

    val projects = MediatorLiveData<List<Project>>()

    private var changeableLiveData = Transformations.map(database.projectDatabaseDao.getAllProjectsLive()) {
        it.asDomainModel()
    }

    init {
        projects.addSource(changeableLiveData) {
            projects.setValue(it)
        }
    }

    // @Throws(Exception::class)
    suspend fun refreshProjects() {
        withContext(Dispatchers.IO) {
            val projects = ProjectApi.retrofitService.getAllProjectsAsync().await()

            // Code to refresh all projects
            //val projects = getAllProjs()
            database.projectDatabaseDao.insertAllProjects(*projects.asDatabaseProjectModel())

            // Code to refresh a single project
            //database.projectDatabaseDao.insert(projects.asDatabaseProject())

            Timber.i("Projects refreshed via API")
        }
    }

}
