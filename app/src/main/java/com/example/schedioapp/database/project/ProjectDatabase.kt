package com.example.schedioapp.database.project

import android.content.Context
import androidx.room.*
import java.util.*

/**
 * Converts dates to strings and vice versa
 */
class DateConverter() {

    @TypeConverter
    fun fromDate(date: Date): String {
        var result: String = dateTimeStringConversion(date)
        return result
    }

    fun toDate(date: String): Date {
        var result: Date = dateTimeDateConversion(date)
        return result
    }

    fun dateTimeStringConversion(date: Date): String {
        val cal: Calendar = GregorianCalendar()
        cal.time = date

        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DATE)
        val timeOffset = "T00:00:00"

        return year.toString() +
                "-" +
                (if (month > 0 && month < 10) "0" + month.toString() else month.toString()) +
                "-" +
                (if (day > 0 && day < 10) "0" + day.toString() else day.toString()) +
                timeOffset
    }

    fun dateTimeDateConversion(date: String): Date {
        val year: Int = date.split("-")[0].toInt()
        val month: Int = date.split("-")[1].toInt()
        val day: Int = date.split("-")[2].split("T")[0].toInt()

        val cal: Calendar = GregorianCalendar(year, month, day)

        return cal.time
    }

}

/**
 * Room database instance for the DatabaseProjects
 */
@Database(entities = [DatabaseProject::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val projectDatabaseDao: ProjectDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getInstance(context: Context): ProjectDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectDatabase::class.java,
                        "custom_joke_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
