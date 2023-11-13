package com.example.lesson_3_safarov.di

import android.app.Application
import android.content.Context
import com.example.lesson_3_safarov.TheApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideApplicationContext(app: TheApplication): Context {
        return app.applicationContext
    }
}