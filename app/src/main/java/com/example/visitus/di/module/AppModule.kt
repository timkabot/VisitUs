package com.example.visitus.di.module

import android.content.Context
import com.example.visitus.model.data.server.ApiService
import com.example.visitus.utils.Constants
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module(){
    init{
        bind(Context::class.java).toInstance(context)

        // Navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(ApiService::class.java).toInstance(ApiService.create(baseUrl = Constants.BASEURL))

        //bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingletonInScope()
    }
}