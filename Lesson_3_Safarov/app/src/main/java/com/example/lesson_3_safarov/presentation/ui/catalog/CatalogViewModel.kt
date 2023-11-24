package com.example.lesson_3_safarov.presentation.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.domain.catalog.Product
import com.example.lesson_3_safarov.domain.catalog.usecase.GetProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _catalogState = MutableLiveData<ResponseStates<List<Product>>>()
    val catalogState: LiveData<ResponseStates<List<Product>>> = _catalogState

    init {
        onLoadData()
    }

    fun onLoadData() = viewModelScope.launch {
        _catalogState.value = ResponseStates.Loading()
        try {
            _catalogState.value = ResponseStates.Success(getProductsUseCase.execute())
        } catch (e: Exception) {
            _catalogState.value = ResponseStates.Failure(e)
        }
    }
}