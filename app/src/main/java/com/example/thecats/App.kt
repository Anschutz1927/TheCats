package com.example.thecats

import android.app.Application
import com.example.thecats.component.AppComponent
import com.example.thecats.component.DaggerAppComponent
import com.example.thecats.repository.StorageRepository

class App : Application() {
    companion object {
        val appComponent: AppComponent = DaggerAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        StorageRepository.init(this)
    }
}