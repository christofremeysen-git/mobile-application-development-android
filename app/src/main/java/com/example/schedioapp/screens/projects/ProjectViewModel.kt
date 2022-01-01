package com.example.schedioapp.screens.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.repository.ProjectRepository
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProjectRepository(ProjectDatabase.getInstance(application.applicationContext))

    // var projects = repository.refreshProjects()

    fun showAllProjects() {
        viewModelScope.launch {
            repository.refreshProjects()
        }
    }

}