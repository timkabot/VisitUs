package com.example.visitus

import android.app.Application
import com.example.visitus.di.DI
import com.example.visitus.di.module.AppModule
import com.example.visitus.di.module.ServerModule
import com.google.gson.Gson
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initToothpick()
        initAppScope()
        initPreferenceHolder()
    }

    private fun initPreferenceHolder(){
        PreferenceHolder.setContext(applicationContext)
        PreferenceHolder.serializer = GsonSerializer(Gson())
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppScope() {
        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(AppModule(applicationContext), ServerModule())
    }
}
