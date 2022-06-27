package com.picpay.desafio.android.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.UserModel
import com.picpay.desafio.android.respository.UserRepository
import com.picpay.desafio.android.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){
    private val _users = MutableStateFlow<ResourceState<List<UserModel>>>(ResourceState.Loading())
    val users: StateFlow<ResourceState<List<UserModel>>> = _users

    init {
        fetch()
    }

    private fun fetch()  = viewModelScope.launch {
        val response = repository.getUsers()
        handleResponse(response)
    }

    private fun handleResponse(response: Call<List<UserModel>>){
        response.enqueue(object : Callback<List<UserModel>>{
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _users.value = ResourceState.Error(null, t.message.toString())
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                response.body()?.let { values ->
                    _users.value = ResourceState.Success(values)
                }
            }
        })
    }
}