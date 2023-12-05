package com.example.lesson_3_safarov.presentation.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_safarov.data.responsemodel.ResponseOrderCreation
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.domain.order.usecase.CreateOrderUseCase
import kotlinx.coroutines.launch
import okhttp3.Response
import java.util.Date
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_COUNTER_STATE = 1
    }

    private val _orderState = MutableLiveData<PreOrderProduct>()
    val orderState: LiveData<PreOrderProduct> = _orderState

    private val _orderCreationResponseState =
        MutableLiveData<ResponseStates<ResponseOrderCreation>>()
    val orderCreationResponseState: LiveData<ResponseStates<ResponseOrderCreation>> =
        _orderCreationResponseState

    val counterState = MutableLiveData(DEFAULT_COUNTER_STATE)
    val currentDate = MutableLiveData<Date>()

    fun onScreenLoad(product: PreOrderProduct) {
        _orderState.value = product
    }

    fun onOrderCreationClicked(
        house: String,
        apartment: String,
        dateDelivery: Date,
        id: String,
        size: String,
        quantity: Int
    ) = viewModelScope.launch {
        _orderCreationResponseState.value = ResponseStates.Loading()
        try {
            _orderCreationResponseState.value = ResponseStates.Success(
                createOrderUseCase.execute(house, apartment, dateDelivery, id, size, quantity)
            )
        } catch (e: Exception) {
            _orderCreationResponseState.value = ResponseStates.Failure(e)
        }
    }
}