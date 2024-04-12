package com.example.lesson_3_safarov.di

import com.example.lesson_3_safarov.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}