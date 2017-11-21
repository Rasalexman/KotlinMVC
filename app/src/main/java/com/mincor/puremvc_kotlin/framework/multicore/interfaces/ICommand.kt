package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface ICommand : INotifier {
    /**
     * Execute the `ICommand`'s logic to handle a given
     * `INotification`.
     *
     * @param notification
     * an `INotification` to handle.
     */
    fun execute(notification: INotification)
}