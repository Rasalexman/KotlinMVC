package com.mincor.puremvc_kotlin.framework.multicore.patterns.observer

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IObserver
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IFunction
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification



/**
 * Created by a.minkin on 21.11.2017.
 */
class Observer(override var notify: IFunction, override var context: Any) : IObserver {

    /**
     * Compare an object to the notification context.
     *
     * @param object
     * the object to compare
     * @return boolean indicating if the object and the notification context are
     * the same
     */
    override fun compareNotifyContext(`object`: Any): Boolean {
        return this.context === `object`
    }

    /**
     * Notify the interested object.
     *
     * @param notification
     * the `INotification` to pass to the interested
     * object's notification method.
     */
    override fun notifyObserver(notification: INotification) {
        this.notify.onNotification(notification)
    }
}