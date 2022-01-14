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
import com.example.schedioapp.network.ProjectApi.mockDeleteProject
import com.example.schedioapp.network.ProjectApi.mockPutProject
import kotlinx.coroutines.*
import retrofit2.await
import timber.log.Timber
import java.lang.Exception

/**
 * The project repository
 *
 * @property database A project database instance
 */
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

    /**
     * Filters added projects
     *
     * @property filter A filter term
     */
    fun addProjectsFilter(filter: String?) {
        this.filter.value = filter
    }

    /**
     * Method used to refresh all projects
     */
    suspend fun refreshProjects() {
        withContext(Dispatchers.IO) {
            val projects = ProjectApi.retrofitService.getAllProjectsAsync().await()
            database.projectDatabaseDao.insertAllProjects(*projects.asDatabaseProjectModel())
            Timber.i("Projects refreshed via API")
        }
    }

    /**
     * Method used to add a project
     *
     * @property newProject A project instance
     * @return A project instance
     */
    suspend fun addProject(newProject: Project): Project {
        val dateConverter = DateConverter()
        val newApiProject = ApiProject(
            id = newProject.id,
            naam = newProject.naam,
            startDatum = dateConverter.fromDate(newProject.startDatum),
            eindDatum = dateConverter.fromDate(newProject.eindDatum),
            budget = newProject.budget,
            status = newProject.status,
            type = newProject.type
        )

        ProjectApi.retrofitService.putProject(newApiProject)
        database.projectDatabaseDao.insert(newApiProject.asDatabaseProject())

        return newProject
    }

    /**
     * Deletes a project
     *
     * @property project A project instance
     */
    suspend fun deleteProject(project: Project) {
        val dateConverter = DateConverter()
        val deletedApiProject = ApiProject(
            id = project.id,
            naam = project.naam,
            startDatum = dateConverter.fromDate(project.startDatum),
            eindDatum = dateConverter.fromDate(project.eindDatum),
            budget = project.budget,
            status = project.status,
            type = project.type
        )

        ProjectApi.retrofitService.deleteProject(deletedApiProject.id)
        database.projectDatabaseDao.delete(deletedApiProject.asDatabaseProject())
    }

}
