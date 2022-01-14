package com.example.schedioapp


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
import java.io.IOException
import kotlin.reflect.typeOf

@RunWith(AndroidJUnit4::class)
class ProjectDatabaseTest {

    private lateinit var db: ProjectDatabase
    private lateinit var projectDao: ProjectDatabaseDao
    private lateinit var projectA: DatabaseProject
    private lateinit var projects: List<DatabaseProject>

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, ProjectDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        projectDao = db.projectDatabaseDao

        projectA = DatabaseProject(
            id = 1000,
            naam = "Testproject A",
            startDatum = "2022-01-01T00",
            eindDatum = "2022-01-10T00",
            budget = 80.0,
            status = "Open",
            type = "Marketing"
        )

        val projectW = DatabaseProject(
            id = 1001,
            naam = "Post app tracking",
            startDatum = "2021-01-15T00",
            eindDatum = "2021-04-15T00",
            budget = 100000.0,
            status = "Voltooid",
            type = "Marketing, Product management"
        )

        val projectX = DatabaseProject(
            id = 1002,
            naam = "Magazine-app",
            startDatum = "2021-03-05T00",
            eindDatum = "2021-07-20T00",
            budget = 250000.0,
            status = "Bezig",
            type = "Marketing, Product management, Technology"
        )
        val projectY = DatabaseProject(
            id = 1003,
            naam = "GDPR audit",
            startDatum = "2021-10-10T00",
            eindDatum = "2021-12-05T00",
            budget = 20000.0,
            status = "Open",
            type = "Finance, General management, R&D"
        )
        val projectZ = DatabaseProject(
            id = 1004,
            naam = "Product management app",
            startDatum = "2021-05-01T00",
            eindDatum = "2021-09-30T00",
            budget = 1500000.0,
            status = "Bezig",
            type = "Product management, R&D, Technology"
        )
        projects = listOf(projectW, projectX, projectY, projectZ)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun deleteAndInsertAllProjects() = runBlocking {
        projectDao.clear()
        assertNull(projectDao.getProject(1))
        projectDao.insertAllProjects(projects)
        val requestedProject = projectDao.getProject(1001)
        val requestedProjects = projectDao.getAllProjects()
        assertEquals(requestedProject.naam, "Post app tracking")
        assertEquals(projects, requestedProjects)
        assertEquals(projects.size, requestedProjects.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetProject() = runBlocking {
        val projectB = DatabaseProject(
            id = 1005,
            naam = "Testproject B",
            startDatum = "2022-01-01T00",
            eindDatum = "2022-01-10T00",
            budget = 80.0,
            status = "Open",
            type = "Marketing"
        )
        projectDao.insert(projectB)
        val addedProject = projectDao.getProject(projectB.id)
        assertEquals(addedProject.naam, "Testproject B")
    }

    @Test
    @Throws(Exception::class)
    fun deleteProject() = runBlocking {
        projectDao.insert(projectA)
        val deletedProject = projectDao.getProject(1000)
        projectDao.delete(deletedProject)
        assertNull(projectDao.getProject(1000))
    }

}