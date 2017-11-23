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
     *
     */
    fun show(popLast:Boolean = false)

    /**
     *
     */
    fun hide(popIt:Boolean = false)

}