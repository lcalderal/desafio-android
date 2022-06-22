package com.picpay.desafio.android.service

import com.picpay.desafio.android.data.model.UserModel
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<UserModel>>
}