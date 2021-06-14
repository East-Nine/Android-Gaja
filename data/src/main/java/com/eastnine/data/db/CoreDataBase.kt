package com.eastnine.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.data.db.entity.PlaceData

@Database(
    entities = [
        PlaceData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CoreDataBase : RoomDatabase() {

    abstract fun travelDao(): TravelDao

    companion object {
        const val DB_NAME = "core.db"
    }
}
