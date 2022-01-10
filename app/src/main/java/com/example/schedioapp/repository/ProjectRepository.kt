package com.example.schedioapp.repository

import android.location.Location
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.schedioapp.database.project.DateConverter
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.asDomainModel
import com.example.schedioapp.domain.Project
import com.example.schedioapp.network.*
import com.example.schedioapp.network.ProjectApi.mockPutJoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import java.lang.Exception

class ProjectRepository(private val database: ProjectDatabase) {

    val projects = MediatorLiveData<List<Project>>()
    val filter = MutableLiveData<String>(null)
    val projectsFilter = Transformations.switchMap(filter) {
        filter -> when(filter) {
            "Open" -> Transformations.map(database.projectDatabaseDao.getAllFilteredProjects("Open")) {
                it.asDomainModel()
            }
            "Bezig" -> Transformations.map(database.projectDatabaseDao.getAllFilteredProjects("Bezig")) {
                it.asDomainModel()
            }
            "Hangende" -> Transformations.map(database.projectDatabaseDao.getAllFilteredProjects("Hangende")) {
                it.asDomainModel()
            }
            "Voltooid" -> Transformations.map(database.projectDatabaseDao.getAllFilteredProjects("Voltooid")) {
                it.asDomainModel()
            }
            else -> Transformations.map(database.projectDatabaseDao.getAllProjectsLive()) {
                it.asDomainModel()
            }
        }
    }

    private var changeableLiveProjectData = Transformations.map(database.projectDatabaseDao.getAllProjectsLive()) {
        it.asDomainModel()
    }

    init {
        projects.addSource(changeableLiveProjectData) {
            projects.setValue(it)
        }
    }

    fun addProjectsFilter(filter: String?) {
        this.filter.value = filter
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

    suspend fun addProject(newProject: Project): Project {
        val dateConverter: DateConverter = DateConverter()
        val newApiProject = ApiProject(
            id = newProject.id,
            naam = newProject.naam,
            startDatum = dateConverter.fromDate(newProject.startDatum),
            eindDatum = dateConverter.fromDate(newProject.eindDatum),
            budget = newProject.budget.toDouble(),
            status = newProject.status,
            type = newProject.type
        )

        ProjectApi.retrofitService.putProject(newApiProject)
        val checkApiProject = ProjectApi.retrofitService.mockPutJoke(newApiProject)
        database.projectDatabaseDao.insert(checkApiProject.asDatabaseProject())

        return newProject
    }

}
