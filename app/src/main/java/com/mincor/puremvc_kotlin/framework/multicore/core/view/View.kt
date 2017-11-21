package com.mincor.puremvc_kotlin.framework.multicore.core.view

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IView
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IMediator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IObserver
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IFunction
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Observer


/**
 * Created by a.minkin on 21.11.2017.
 */
open class View(var multitonKey: String) : IView {

    // Mapping of Mediator names to Mediator instances
    // Mapping of Notification names to Observer lists
    private val observerMap: MutableMap<String, MutableList<IObserver>> = mutableMapOf()
    private val mediatorMap: MutableMap<String, IMediator> = mutableMapOf()

    companion object {
        private val instanceMap: MutableMap<String, View> = mutableMapOf()
        /**
         * View Singleton Factory method.
         *
         * @return the Singleton instance of `View`
         */
        @Synchronized
        fun getInstance(key: String): View {
            return instanceMap.getOrPut(key){View(key)}
        }

        /**
         * Remove an IView instance
         *
         * @param multitonKey of IView instance to remove
         */
        @Synchronized
        fun removeView(key: String) {
            instanceMap.remove(key)
        }
    }


    /**
     * Constructor.
     *
     * <P>
     * This `IView` implementation is a Multiton,
     * so you should not call the constructor
     * directly, but instead call the static Multiton
     * Factory method `View.getInstance( multitonKey )`
     *
     * @throws Error Error if instance for this Multiton key has already been constructed
    </P> */
    init {
        instanceMap.put(multitonKey, this)
        initializeView()
    }

    /**
     * Initialize the Singleton View instance.
     *
     * <P>
     * Called automatically by the constructor, this is your opportunity to
     * initialize the Singleton instance in your subclass without overriding
     * the constructor.
    </P> *
     *
     */
    protected fun initializeView() {}


    /**
     * Notify the `Observers` for a particular
     * `Notification`.
     *
     * <P>
     * All previously attached `Observers` for this
     * `Notification`'s list are notified and are passed a
     * reference to the `Notification` in the order in which they
     * were registered.
    </P> *
     *
     * @param note
     * the `Notification` to notify
     * `Observers` of.
     */
    override fun notifyObservers(note: INotification) {
        val observers = this.observerMap[note.name] as List<IObserver>
        observers.let {
            // Copy observers from reference array to working array,
            // since the reference array may change during the
            // notification loop
            val workingObservers = it.toTypedArray()
            // Notify Observers from the working array
            workingObservers.forEachIndexed { _, iObserver ->
                iObserver.notifyObserver(note)
            }
        }
    }

    /**
     * Remove the observer for a given notifyContext from an observer list for a given Notification name.
     * <P>
     * @param notificationName which observer list to remove from
     * @param notifyContext remove the observer with this object as its notifyContext
    </P> */
    fun removeObserver(notificationName: String, notifyContext: Any) {
        // the observer list for the notification under inspection
        val observers = observerMap[notificationName]

        observers?.let {
            // find the observer for the notifyContext
            it.forEachIndexed { _, iObserver ->
                if (iObserver.compareNotifyContext(notifyContext)) {
                    it.remove(iObserver)
                }
            }
            // Also, when a Notification's Observer list length falls to
            // zero, delete the notification key from the observer map
            if (it.isEmpty()) {
                observerMap.remove(notificationName)
            }
        }
    }

    /**
     * Register an `Mediator` instance with the `View`.
     *
     * <P>
     * Registers the `Mediator` so that it can be retrieved by
     * name, and further interrogates the `Mediator` for its
     * `Notification` interests.
    </P> *
     * <P>
     * If the `Mediator` returns any `Notification`
     * names to be notified about, an `Observer` is created
     * encapsulating the `Mediator` instance's
     * `handleNotification` method and registering it as an
     * `Observer` for all `Notifications` the
     * `Mediator` is interested in.
    </P> *
     *
     * @param mediator
     * the name to associate with this `IMediator`
     * instance
     */
    override fun registerMediator(mediator: IMediator) {
        if (!this.mediatorMap.containsKey(mediator.mediatorName)) {
            mediator.initializeNotifier(multitonKey)

            // Register the Mediator for retrieval by name
            this.mediatorMap.put(mediator.mediatorName, mediator)

            // Get Notification interests, if any.
            val noteInterests = mediator.listNotificationInterests()
            if (noteInterests.isNotEmpty()) {
                // Create java style function ref to mediator.handleNotification
                val function = object : IFunction {
                    override fun onNotification(notification: INotification) {
                        mediator.handleNotification(notification)
                    }
                }

                // Create Observer
                val observer = Observer(function, mediator)

                // Register Mediator as Observer for its list of Notification
                // interests
                noteInterests.forEachIndexed { _, s ->
                    registerObserver(s, observer)
                }
            }

            // alert the mediator that it has been registered
            mediator.onRegister()
        }
    }

    /**
     * Register an `Observer` to be notified of
     * `INotifications` with a given name.
     *
     * @param noteName
     * the name of the `Notifications` to notify this
     * `Observer` of
     * @param observer
     * the `Observer` to register
     */
    override fun registerObserver(noteName: String, observer: IObserver) {
        val observers = this.observerMap.getOrPut(noteName){mutableListOf()}
        observers.add(observer)
    }

    /**
     * Remove an `Mediator` from the `View`.
     *
     * @param mediatorName
     * name of the `Mediator` instance to be removed.
     */
    override fun removeMediator(mediatorName: String): IMediator? {
        // Retrieve the named mediator
        val mediator = mediatorMap[mediatorName]

        mediator?.let{
            // for every notification this mediator is interested in...
            val interests = it.listNotificationInterests()
            // remove the observer linking the mediator
            // to the notification interest
            interests.forEachIndexed { _, s ->
                removeObserver(s, mediator)
            }
            // remove the mediator from the map
            mediatorMap.remove(mediatorName)
            // alert the mediator that it has been removed
            it.onRemove()
        }
        return mediator
    }

    /**
     * Retrieve an `Mediator` from the `View`.
     *
     * @param mediatorName
     * the name of the `Mediator` instance to
     * retrieve.
     * @return the `Mediator` instance previously registered with
     * the given `mediatorName`.
     */
    override fun retrieveMediator(mediatorName: String): IMediator {
        return this.mediatorMap[mediatorName] as IMediator
    }

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName
     * @return whether a Mediator is registered with the given `mediatorName`.
     */
    override fun hasMediator(mediatorName: String): Boolean {
        return mediatorMap.containsKey(mediatorName)
    }
}