package com.mincor.puremvc_kotlin.framework.multicore.patterns.mediator

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IMediator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notifier


/**
 * Created by a.minkin on 21.11.2017.
 */
abstract class Mediator(override val mediatorName: String) : Notifier(), IMediator {

    override var viewComponent: View? = null
    override val container: ViewGroup get() = getFacade().currentContainer!!
    override val activity: Activity get() =  getFacade().currentActivity!!

    /**
     * Handle `INotification`s.
     *
     * <P>
     * Typically this will be handled in a switch statement, with one 'case'
     * entry per `INotification` the `Mediator` is
     * interested in.
     * @param notification
    </P> */
    override fun handleNotification(notification: INotification) {}

    /**
     * List the `INotification` names this `Mediator`
     * is interested in being notified of.
     *
     * @return String[] the list of `INotification` names
     */
    override fun listNotificationInterests(): Array<String> {
        return arrayOf()
    }

    /**
     * Called by the View when the Mediator is registered.
     */
    override fun onRegister() {
        viewComponent?.parent?:container.addView(viewComponent)
    }

    /**
     * Called by the View when the Mediator is removed.
     */
    override fun onRemove() {
        container.removeView(viewComponent)
        viewComponent = null
    }
}