package com.mincor.puremvc_kotlin.controllers

import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.command.SimpleCommand
import com.mincor.puremvc_kotlin.models.UserProxy
import com.mincor.puremvc_kotlin.views.*

/**
 * Created by a.minkin on 21.11.2017.
 */
class StartupCommand : SimpleCommand() {

    override fun execute(notification: INotification) {
        getFacade().registerProxy(UserProxy())

        getFacade().registerMediator(UserAuthMediator())
        getFacade().registerMediator(UserListsMediator())

        getFacade().removeCommand(AppFacade.STARTUP)
    }
}