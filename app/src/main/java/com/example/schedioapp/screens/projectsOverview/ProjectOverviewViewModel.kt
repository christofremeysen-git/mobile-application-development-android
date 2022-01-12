package com.example.schedioapp.screens.projectsOverview

import android.app.Application
import androidx.lifecycle.*
import com.example.schedioapp.database.project.DatabaseProject
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.ProjectDatabaseDao
import com.example.schedioapp.domain.Project
import com.example.schedioapp.repository.ProjectRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProjectOverviewViewModel(val database: ProjectDatabaseDao, application: Application): AndroidViewModel(application){

    private var currentFilter: String? = null

    val db = ProjectDatabase.getInstance(application.applicationContext)
    val repository = ProjectRepository(db)

    val projects = repository.projectsFilter

    fun filterChip(changedFilter: String, isChecked: Boolean) {
        if(isChecked) {
            currentFilter = changedFilter
        } else if(currentFilter == changedFilter) {
            currentFilter = null
        }
        repository.addProjectsFilter(currentFilter)
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            deleteProjectWithRepository(project)
        }
    }

    suspend fun deleteProjectFromDatabase(project: DatabaseProject) {
        database.delete(project)
    }

    suspend fun deleteProjectWithRepository(project: Project) {
        repository.deleteProject(project)
    }

}