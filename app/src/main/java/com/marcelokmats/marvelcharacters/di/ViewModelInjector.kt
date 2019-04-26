package com.marcelokmats.marvelcharacters.di

import android.app.Application
import com.marcelokmats.marvelcharacters.MainViewModel
import com.marcelokmats.marvelcharacters.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    NetworkModule::class
])
interface ViewModelInjector {

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        @BindsInstance
        fun application(application: Application): Builder
    }
}
