package com.example.thecatapi

import android.app.Application
import com.example.thecatapi.dagger.AppComponent
import com.example.thecatapi.dagger.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .databaseModule(AppComponent.DatabaseModule(applicationContext))
            .build()
    }
}