package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
/**
 * This interface must be implemented by all classes that want to be notified of
 * a notification.
 */
interface IFunction {
    /**
     * @param notification
     */
    fun onNotification(notification: INotification)
}