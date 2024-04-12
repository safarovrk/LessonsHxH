package com.example.lesson_3_safarov.presentation.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.domain.product.Product
import com.example.lesson_3_safarov.domain.product.usecase.GetProductUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _productState = MutableLiveData<ResponseStates<Product>>()
    val productState: LiveData<ResponseStates<Product>> = _productState
    val currentSelectedSize = MutableLiveData<String>()

    fun onLoadData(id: String) = viewModelScope.launch {
        _productState.value = ResponseStates.Loading()
        try {
            _productState.value = ResponseStates.Success(getProductUseCase.execute(id))
        } catch (e: Exception) {
            _productState.value = ResponseStates.Failure(e)
        }
    }
}