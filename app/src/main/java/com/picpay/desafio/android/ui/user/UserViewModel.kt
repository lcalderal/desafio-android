package com.picpay.desafio.android.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.respository.UserRepository
import com.picpay.desafio.android.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){
    private val _users = MutableStateFlow<ResourceState<UserResponse>>(ResourceState.Loading())
    val users: StateFlow<ResourceState<UserResponse>> = _users

    init {
        fetch()
    }

    private fun fetch()  = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.getUsers()
            _users.value = handleResponse(response)
        }
        catch (t: Throwable) {
            when(t) {
                is IOException -> _users.value = ResourceState.Error(null,"Error de conexão")
                else -> _users.value = ResourceState.Error( null,"Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<UserResponse>): ResourceState<UserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(null, response.message())
    }
}