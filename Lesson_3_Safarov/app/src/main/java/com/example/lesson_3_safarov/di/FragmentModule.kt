package com.example.lesson_3_safarov.di

import com.example.lesson_3_safarov.presentation.ui.catalog.CatalogFragment
import com.example.lesson_3_safarov.presentation.ui.order.OrderFragment
import com.example.lesson_3_safarov.presentation.ui.product.ProductFragment
import com.example.lesson_3_safarov.presentation.ui.product.size.SizeBottomSheetFragment
import com.example.lesson_3_safarov.presentation.ui.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun catalogFragment(): CatalogFragment

    @ContributesAndroidInjector
    abstract fun productFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun sizeBottomSheetFragment(): SizeBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun orderFragment(): OrderFragment
}