package com.marcelokmats.marvelcharacters.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.marcelokmats.marvelcharacters.MainViewModel
import com.marcelokmats.marvelcharacters.di.module.NetworkModule


abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .application(application)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }
}