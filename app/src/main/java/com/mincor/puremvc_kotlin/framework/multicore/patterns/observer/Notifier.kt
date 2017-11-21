package com.mincor.puremvc_kotlin.framework.multicore.patterns.observer

import com.mincor.puremvc_kotlin.framework.multicore.patterns.facade.Facade

/**
 * Created by a.minkin on 21.11.2017.
 */
open class Notifier {

    // The Multiton Key for this app
    protected var multitonKey: String? = null

    protected fun getFacade(): Facade {
        if (multitonKey == null) {
            throw RuntimeException("Notifier not initialized")
        }
        return Facade.getInstance(multitonKey!!)
    }

    /**
     * Send an `INotification`s.
     *
     * <P>
     * Keeps us from having to construct new notification instances in our
     * implementation code.
     *
     * @param notificationName
     * the name of the notiification to send
     * @param body
     * the body of the notification (optional)
     * @param type
     * the type of the notification (optional)
    </P> */
    fun sendNotification(notificationName: String, body: Any?, type: String?) {
        getFacade().sendNotification(notificationName, body, type)
    }

    /**
     * Initialize this INotifier instance.
     * <P>
     * This is how a Notifier gets its multitonKey.
     * Calls to sendNotification or to access the
     * facade will fail until after this method
     * has been called.</P>
     *
     * <P>
     * Mediators, Commands or Proxies may override
     * this method in order to send notifications
     * or access the Multiton Facade instance as
     * soon as possible. They CANNOT access the facade
     * in their constructors, since this method will not
     * yet have been called.</P>
     *
     * @param key the multitonKey for this INotifier to use
     */
    fun initializeNotifier(key: String) {
        multitonKey = key
    }
}