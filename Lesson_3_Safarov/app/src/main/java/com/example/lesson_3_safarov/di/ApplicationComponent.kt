package com.example.lesson_3_safarov.di

import com.example.lesson_3_safarov.TheApplication
import com.example.lesson_3_safarov.presentation.ui.signin.SignInFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<TheApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: TheApplication): ApplicationComponent
    }
}