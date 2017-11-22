package com.mincor.puremvc_kotlin.controllers

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.command.SimpleCommand
import com.mincor.puremvc_kotlin.models.UserProxy

/**
 * Created by a.minkin on 22.11.2017.
 */
class UserAuthCommand : SimpleCommand() {
    override fun execute(notification: INotification) {
        val userProxy:UserProxy = getFacade().retrieveProxy(UserProxy.NAME) as UserProxy
        val params:Array<String> = notification.body as Array<String>
        userProxy.authorization(params[0], params[1])
    }
}