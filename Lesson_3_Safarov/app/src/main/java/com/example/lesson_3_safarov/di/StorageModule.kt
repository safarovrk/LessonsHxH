package com.example.lesson_3_safarov.di

import android.content.Context
import androidx.room.Room
import com.example.lesson_3_safarov.data.storage.AppDatabase
import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.getProductDao()
    }
}