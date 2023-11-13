package com.example.lesson_3_safarov.di

import com.example.lesson_3_safarov.presentation.ui.catalog.CatalogFragment
import com.example.lesson_3_safarov.presentation.ui.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun catalogFragment(): CatalogFragment
}