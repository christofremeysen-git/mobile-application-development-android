package com.example.schedioapp.screens.projectsOverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.schedioapp.database.project.ProjectDatabaseDao
import java.lang.IllegalArgumentException

class ProjectOverviewViewModelFactory(private val dataSource: ProjectDatabaseDao, private val application: Application, private val adapter: ProjectListAdapter): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProjectOverviewViewModel::class.java)) {
            return ProjectOverviewViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}