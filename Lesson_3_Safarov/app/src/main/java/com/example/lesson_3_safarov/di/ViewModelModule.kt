package com.example.lesson_3_safarov.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lesson_3_safarov.presentation.ui.catalog.CatalogViewModel
import com.example.lesson_3_safarov.presentation.ui.order.OrderViewModel
import com.example.lesson_3_safarov.presentation.ui.product.ProductViewModel
import com.example.lesson_3_safarov.presentation.ui.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun signInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    abstract fun catalogViewModel(catalogViewModel: CatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun productViewModel(catalogViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun orderViewModel(catalogViewModel: OrderViewModel): ViewModel
}