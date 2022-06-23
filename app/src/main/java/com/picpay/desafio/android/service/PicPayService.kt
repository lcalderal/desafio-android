package com.picpay.desafio.android.service

import com.picpay.desafio.android.data.model.UserModel
import com.picpay.desafio.android.data.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Response<UserResponse>
//    fun getUsers(): Call<List<UserModel>>
}