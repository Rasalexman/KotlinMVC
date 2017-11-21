package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IMediator : INotifier {
    val mediatorName: String
    val viewComponent: Any?
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
     * Called by the View when the Mediator is registered.
     */
    fun onRegister()

    /**
     * Called by the View when the Mediator is removed.
     */
    fun onRemove()
}