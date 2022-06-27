package com.picpay.desafio.android.respository

import com.picpay.desafio.android.service.PicPayService
import javax.inject.Inject

/**Onde contém a fonte de dados, nesse caso, onde tem o método que lista os usuários**/
class UserRepository @Inject constructor(private val api: PicPayService) {
    fun getUsers() = api.getUsers()
}