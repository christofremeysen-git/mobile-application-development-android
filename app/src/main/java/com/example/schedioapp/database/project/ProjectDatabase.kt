package com.example.schedioapp.database.project

import android.content.Context
import androidx.room.*
import java.util.*

class DateConverter() {

    // https://developer.android.com/training/data-storage/room/referencing-data
    // https://stackoverflow.com/questions/50313525/room-using-date-field
    // https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html
    // https://developer.android.com/training/data-storage/room/relationships#one-to-many
    @TypeConverter
    fun fromDate(date: Date): String {
        var result: String = dateTimeConversion(date)
        return date?.let { date?.toString() }
    }

    fun dateTimeConversion(date: Date): String {
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

}

@Database(entities = [DatabaseProject::class, DatabaseTaak::class], version = 1, exportSchema = false)
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