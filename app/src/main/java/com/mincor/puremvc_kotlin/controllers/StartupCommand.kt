package com.mincor.puremvc_kotlin.controllers

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.command.SimpleCommand
import com.mincor.puremvc_kotlin.models.UserProxy

/**
 * Created by a.minkin on 21.11.2017.
 */
class StartupCommand : SimpleCommand() {

    override fun execute(notification: INotification) {
        getFacade().registerProxy(UserProxy())
    }
}