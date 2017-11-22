package com.mincor.puremvc_kotlin.controllers

import android.view.ViewGroup
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.command.SimpleCommand
import com.mincor.puremvc_kotlin.models.UserProxy
import com.mincor.puremvc_kotlin.views.UserAuthMediator

/**
 * Created by a.minkin on 21.11.2017.
 */
class StartupCommand : SimpleCommand() {

    override fun execute(notification: INotification) {
        getFacade().registerProxy(UserProxy())
        getFacade().registerMediator(UserAuthMediator(notification.body as ViewGroup))

        getFacade().removeCommand(AppFacade.STARTUP)
    }
}