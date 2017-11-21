package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IObserver {
    var context: Any
    var notify: IFunction

    /**
     * Notify the interested object.
     *
     * @param notification
     * the `INotification` to pass to the interested
     * object's notification method
     */
    fun notifyObserver(notification: INotification)

    /**
     * Compare the given object to the notificaiton context object.
     *
     * @param object
     * the object to compare.
     * @return boolean indicating if the notification context and the object are
     * the same.
     */
    fun compareNotifyContext(`object`: Any): Boolean
}