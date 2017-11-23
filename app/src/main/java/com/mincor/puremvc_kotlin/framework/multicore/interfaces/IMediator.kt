package com.mincor.puremvc_kotlin.framework.multicore.interfaces

import android.view.View

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IMediator : INotifier {
    val mediatorName: String
    var viewComponent: View?

    /**
     * List `INotification` interests.
     *
     * @return an `Array` of the `INotification` names
     * this `IMediator` has an interest in.
     */
    fun listNotificationInterests(): Array<String>

    /**
     * Handle an `INotification`.
     *
     * @param notification
     * the `INotification` to be handled
     */
    fun handleNotification(notification: INotification)

    /**
     * Called by the register mediator once
     */
    fun onCreateView()

    /**
     * Called by the View when the Mediator is registered.
     */
    fun onRegister()

    /**
     * Called by the View when the Mediator is removed.
     */
    fun onRemove()

    /**
     * Add current mediator to view stage
     *
     * @param popLast
     * Flag that indicates to need remove last showing mediator from backstack
     */
    fun show(popLast: Boolean = false)

    /**
     * Remove current mediator from view stage
     *
     * @param popIt
     * Flag that indicates to need remove current mediator from backstack
     */
    fun hide(popIt: Boolean = false)

    /**
     * Hide current mediator and remove it from backstack
     * Then show last added mediator from backstack
     */
    fun popToBack()

}