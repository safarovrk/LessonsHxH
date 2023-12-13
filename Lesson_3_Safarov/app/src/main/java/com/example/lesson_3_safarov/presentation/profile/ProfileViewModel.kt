package com.example.lesson_3_safarov.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.domain.profile.Profile
import com.example.lesson_3_safarov.domain.profile.usecase.GetProfile
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfile: GetProfile
) : ViewModel() {

    private val _profileState = MutableLiveData<ResponseStates<Profile>>()
    val profileState: LiveData<ResponseStates<Profile>> = _profileState

    init {
        onLoadData()
    }

    private fun onLoadData() = viewModelScope.launch {
        _profileState.value = ResponseStates.Loading()
        try {
            _profileState.value = ResponseStates.Success(getProfile.execute())
        } catch (e: Exception) {
            _profileState.value = ResponseStates.Failure(e)
        }
    }

}