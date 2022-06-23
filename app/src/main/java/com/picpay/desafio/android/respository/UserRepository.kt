package com.picpay.desafio.android.respository

import com.picpay.desafio.android.service.PicPayService
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: PicPayService) {
    suspend fun getUsers() = api.getUsers()
}