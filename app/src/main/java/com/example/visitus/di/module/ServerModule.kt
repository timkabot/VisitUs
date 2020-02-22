package com.example.visitus.di.module

import com.example.visitus.model.data.server.ApiService
import com.example.visitus.utils.Constants
import toothpick.config.Module

class ServerModule : Module(){
    init {
        bind(ApiService::class.java).toInstance(ApiService.create(baseUrl = Constants.BASEURL))
    }
}