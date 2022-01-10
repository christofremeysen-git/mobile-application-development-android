package com.example.schedioapp.screens.addProject

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.schedioapp.database.project.ProjectDatabaseDao

class AddProjectViewModelFactory(private val dataSource: ProjectDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddProjectViewModel::class.java)) {
            return AddProjectViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}