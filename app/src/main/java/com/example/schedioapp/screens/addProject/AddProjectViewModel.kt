package com.example.schedioapp.screens.addProject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.schedioapp.database.project.DatabaseProject
import com.example.schedioapp.database.project.DateConverter
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.ProjectDatabaseDao
import com.example.schedioapp.domain.Project
import com.example.schedioapp.repository.ProjectRepository
import kotlinx.coroutines.launch
import java.util.*

class AddProjectViewModel(val database: ProjectDatabaseDao, application: Application): AndroidViewModel(application) {
    private val _projectSubmitEvent = MutableLiveData<Boolean>()
    val projectSubmitEvent: LiveData<Boolean>
        get() = _projectSubmitEvent

    private val db = ProjectDatabase.getInstance(application.applicationContext)
    private val repository = ProjectRepository(db)

    init {
        _projectSubmitEvent.value = false
    }

    fun submitProjectCLick() {
        _projectSubmitEvent.value = true
    }

    fun submitEventDone() {
        _projectSubmitEvent.value = false
    }

    fun submitProject(naam: String, startDatum: Date, eindDatum: Date, budget: Double, status: String, type: String) {
        viewModelScope.launch {
            val project = Project(
                id = 0,
                naam = naam,
                startDatum = startDatum,
                eindDatum = eindDatum,
                budget = budget,
                status = status,
                type = type
            )
            saveProjectWithRepository(project)
        }
    }

    suspend fun saveProjectToDatabase(newProject: DatabaseProject) {
        database.insert(newProject)
    }

    suspend fun saveProjectWithRepository(newProject: Project) {
        repository.addProject(newProject)
    }

}