package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface INotification {
    // the name of the notification instance
    var name: String
    // the type of the notification instance
    var type: String?
    // the body of the notification instance
    var body: Any?

    /**
     * Get the string representation of the `INotification`
     * instance.
     *
     * @return the string representation of the `INotification`
     * instance
     */
    override fun toString(): String
}