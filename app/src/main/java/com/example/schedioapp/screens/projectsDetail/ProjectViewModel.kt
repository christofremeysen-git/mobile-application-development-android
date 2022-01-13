package com.example.schedioapp.screens.projectsDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.domain.Project
import com.example.schedioapp.repository.ProjectRepository

class ProjectViewModel(application: Application): AndroidViewModel(application) {

    private val _currentProject = MutableLiveData<Project>()
    val currentProject: LiveData<Project>
        get() = _currentProject

    private val repository = ProjectRepository(ProjectDatabase.getInstance(application.applicationContext))

}
