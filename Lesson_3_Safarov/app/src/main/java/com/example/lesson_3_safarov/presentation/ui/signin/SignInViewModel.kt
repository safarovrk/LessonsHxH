package com.example.lesson_3_safarov.presentation.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.domain.signin.SignInUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _signInState = MutableLiveData<ResponseStates<ResponseLogin>>()
    val signInState: LiveData<ResponseStates<ResponseLogin>> = _signInState

    fun onSignIn(email: String, password: String) = viewModelScope.launch {
        _signInState.value = ResponseStates.Loading()
        try {
            _signInState.value = ResponseStates.Success(
                signInUseCase.execute(email, password)
            )
        } catch (e: Exception) {
            _signInState.value = ResponseStates.Failure(e)
        }
    }
}