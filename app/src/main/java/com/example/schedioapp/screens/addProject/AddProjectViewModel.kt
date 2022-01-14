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

    /**
     * Registers a click on the submit button
     */
    fun submitProjectCLick() {
        _projectSubmitEvent.value = true
    }

    /**
     * Checks whether the submit event has been performed
     */
    fun submitEventDone() {
        _projectSubmitEvent.value = false
    }

    /**
     * Method used to add a project
     *
     * @property naam The project name
     * @property startDatum The start date of the project
     * @property eindDatum The end date of the project
     * @property budget The project budget
     * @property status The project status
     * @property type The project type
     */
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

    /**
     * Method used to save a project into the database
     *
     * @property newProject A database project
     */
    suspend fun saveProjectToDatabase(newProject: DatabaseProject) {
        database.insert(newProject)
    }

    /**
     * Method used to save a project by means of the repository (API)
     *
     * @property newProject A project instance
     */
    suspend fun saveProjectWithRepository(newProject: Project) {
        repository.addProject(newProject)
    }

}