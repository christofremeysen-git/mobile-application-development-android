package com.example.schedioapp.screens.projectsOverview

import android.app.Application
import androidx.lifecycle.*
import com.example.schedioapp.database.project.DatabaseProject
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.ProjectDatabaseDao
import com.example.schedioapp.domain.Project
import com.example.schedioapp.repository.ProjectRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectOverviewViewModel(val database: ProjectDatabaseDao, application: Application): AndroidViewModel(application){

    var currentProject = 1

    private val _status = MutableLiveData<ProjectApiStatus>()
    val status: LiveData<ProjectApiStatus>
        get() = _status

    private var currentFilter: String? = null

    val db = ProjectDatabase.getInstance(application.applicationContext)
    val repository = ProjectRepository(db)

    val projects = repository.projectsFilter

    init {
        Timber.i("Loading projects")
        viewModelScope.launch {
            _status.value = ProjectApiStatus.LOADING
            repository.refreshProjects()
            _status.value = ProjectApiStatus.DONE
        }
    }

    /**
     * Method used to filter projects in the overview
     *
     * @property changedFilter Used to check if a filter option has changed
     * @property isChecked Used to check whether a filter option is enabled
     */
    fun filterChip(changedFilter: String, isChecked: Boolean) {
        if(isChecked) {
            currentFilter = changedFilter
        } else if(currentFilter == changedFilter) {
            currentFilter = null
        }
        repository.addProjectsFilter(currentFilter)
    }

    /**
     * Method used to delete a project by means of the repository
     *
     * @property project A project instance
     */
    fun deleteProject(project: Project) {
        viewModelScope.launch {
            deleteProjectWithRepository(project)
        }
    }

    /**
     * Method used to delete a project from the database
     *
     * @property project A database project
     */
    suspend fun deleteProjectFromDatabase(project: DatabaseProject) {
        database.delete(project)
    }

    /**
     * Method used to delete a project by means of the repository (API call)
     *
     * @property project A project instance
     */
    suspend fun deleteProjectWithRepository(project: Project) {
        repository.deleteProject(project)
    }

}