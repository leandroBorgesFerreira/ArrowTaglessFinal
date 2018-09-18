package br.com.leandro.arrowtaglessfinal.application

import android.app.Application
import br.com.leandro.arrowtaglessfinal.retrofit.initRetrofit

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initRetrofit(this)
    }
}
