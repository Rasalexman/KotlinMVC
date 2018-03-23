package com.mincor.puremvc_kotlin.controllers

import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.models.UserProxy
import com.mincor.puremvc_kotlin.views.UserAuthMediator
import com.mincor.puremvc_kotlin.views.UserListsMediator
import com.rasalexman.kotlinmvc.interfaces.INotification
import com.rasalexman.kotlinmvc.patterns.command.SimpleCommand

/**
 * Created by a.minkin on 21.11.2017.
 */
class StartupCommand : SimpleCommand() {

    override fun execute(notification: INotification) {
        facade.registerProxy(UserProxy())

        facade.registerMediator(UserAuthMediator())
        facade.registerMediator(UserListsMediator())

        facade.removeCommand(AppFacade.STARTUP)
    }
}