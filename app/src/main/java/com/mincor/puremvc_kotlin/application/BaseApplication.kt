package com.mincor.puremvc_kotlin.application

import android.app.Application
import com.mincor.puremvc_kotlin.facades.AppFacade

/**
 * Created by a.minkin on 21.11.2017.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppFacade().startup()
    }
}