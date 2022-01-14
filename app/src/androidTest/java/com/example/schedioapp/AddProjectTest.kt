package com.example.schedioapp

import android.app.Application
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.schedioapp.database.project.DatabaseProject
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.database.project.ProjectDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import kotlin.reflect.typeOf

// Under construction
@RunWith(MockitoJUnitRunner::class)
class AddProjectTest {

    @Test
    @Throws(Exception::class)
    fun validProject() {

        val elements = listOf(
            DatabaseProject(
                id = 1001,
                naam = "Post app tracking",
                startDatum = "2021-01-15T00",
                eindDatum = "2021-04-15T00",
                budget = 100000.0,
                status = "Voltooid",
                type = "Marketing, Product management"
            ),
            DatabaseProject(
                id = 1002,
                naam = "Magazine-app",
                startDatum = "2021-03-05T00",
                eindDatum = "2021-07-20T00",
                budget = 250000.0,
                status = "Bezig",
                type = "Marketing, Product management, Technology"
            )
        )

        // val db = Mockito.mock(ProjectDatabase::class.java)
        // val application = Application()
        // val vm = AddProjectViewModel(db)

        // val dbMock = Mockito.mock(ProjectDatabase::class.java)
        // val vm = AddProjectViewodel(dbMock, application: Application)

        // Mockito.when(vm.saveProjectWithRepository(newProject: Project)).thenReturn(elements)


    }

}