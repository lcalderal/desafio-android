package com.picpay.desafio.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**Aqui que o Hilt vai gerar o código de nível de aplicação e fornecer todas as dependências para qualquer entidade**/
@HiltAndroidApp
class UserApplication : Application() {
}