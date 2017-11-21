package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IController {
    /**
     * Register a particular `ICommand` class as the handler for a
     * particular `INotification`.
     *
     * @param notificationName
     * the name of the `INotification`
     * @param command
     * the Class of the `ICommand`
     */
    fun registerCommand(notificationName: String, command: ICommand)

    /**
     * Execute the `ICommand` previously registered as the handler
     * for `INotification`s with the given notification name.
     *
     * @param notification
     * the `INotification` to execute the associated
     * `ICommand` for
     */
    fun executeCommand(notification: INotification)

    /**
     * Remove a previously registered `ICommand` to
     * `INotification` mapping.
     *
     * @param notificationName
     * the name of the `INotification` to remove the
     * `ICommand` mapping for
     */
    fun removeCommand(notificationName: String)

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName
     * @return whether a Command is currently registered for the given `notificationName`.
     */
    fun hasCommand(notificationName: String): Boolean
}