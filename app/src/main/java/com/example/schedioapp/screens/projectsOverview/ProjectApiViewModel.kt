package com.example.schedioapp.screens.projectsOverview

import android.app.Application
import androidx.lifecycle.*
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.repository.ProjectRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalArgumentException

enum class ProjectApiStatus { LOADING, ERROR, DONE }

class ProjectApiViewModel(application: Application): AndroidViewModel(application) {

    private val _status = MutableLiveData<ProjectApiStatus>()
    val status: LiveData<ProjectApiStatus>
        get() = _status

    private val database = ProjectDatabase.getInstance(application.applicationContext)
    private val projectRepository = ProjectRepository(database)

    val projects = projectRepository.projects

    init {
        Timber.i("Loading projects")
        viewModelScope.launch {
            _status.value = ProjectApiStatus.LOADING
            projectRepository.refreshProjects()
            _status.value = ProjectApiStatus.DONE
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProjectApiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProjectApiViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}

