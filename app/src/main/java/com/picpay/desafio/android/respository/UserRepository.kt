package com.picpay.desafio.android.respository

import com.picpay.desafio.android.service.PicPayService
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: PicPayService) {
    fun getUsers() = api.getUsers()
}