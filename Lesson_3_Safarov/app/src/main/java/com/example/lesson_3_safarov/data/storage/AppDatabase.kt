package com.example.lesson_3_safarov.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lesson_3_safarov.data.storage.AppDatabase.Companion.DATABASE_VERSION
import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import com.example.lesson_3_safarov.data.storage.entities.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "database.db"
        const val DATABASE_VERSION = 1
    }

    abstract fun getProductDao(): ProductDao
}