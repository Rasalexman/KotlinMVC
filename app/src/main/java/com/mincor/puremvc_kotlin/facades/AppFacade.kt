package com.mincor.puremvc_kotlin.facades

import com.mincor.puremvc_kotlin.controllers.StartupCommand
import com.mincor.puremvc_kotlin.framework.multicore.patterns.facade.Facade

/**
 * Created by a.minkin on 21.11.2017.
 */
class AppFacade : Facade(NAME) {

    companion object {
        val NAME = "APP_FACADE"
        val STARTUP = "startup"
    }

    override fun initializeController() {
        super.initializeController()
        registerCommand(STARTUP, StartupCommand())
    }
}