package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */

/**
 * The interface definition for a PureMVC Notifier.
 *
 * <P>
 * <code>MacroCommand, Command, Mediator</code> and <code>Proxy</code> all
 * have a need to send <code>Notifications</code>.
 * </P>
 *
 * <P>
 * The <code>INotifier</code> interface provides a common method called
 * <code>sendNotification</code> that relieves implementation code of the
 * necessity to actually construct <code>Notifications</code>.
 * </P>
 *
 * <P>
 * The <code>Notifier</code> class, which all of the above mentioned classes
 * extend, also provides an initialized reference to the <code>Facade</code>
 * Singleton, which is required for the convienience method for sending
 * <code>Notifications</code>, but also eases implementation as these classes
 * have frequent <code>Facade</code> interactions and usually require access
 * to the facade anyway.
 * </P>
 *
 * @see  IFacade
 * @see  INotification
 */
interface INotifier {
    /**
     * Send a `INotification`.
     *
     * <P>
     * Convenience method to prevent having to construct new notification
     * instances in our implementation code.
    </P> *
     *
     * @param notificationName
     * the name of the notification to send
     * @param body
     * the body of the notification (optional)
     * @param type
     * the type of the notification (optional)
     */
    fun sendNotification(notificationName: String, body: Any? = null, type: String? = null)

    /**
     * Initialize this INotifier instance.
     * <P>
     * This is how a Notifier gets its multitonKey.
     * Calls to sendNotification or to access the
     * facade will fail until after this method
     * has been called.</P>
     *
     * @param key the multitonKey for this INotifier to use
     */
    fun initializeNotifier(key: String)
}