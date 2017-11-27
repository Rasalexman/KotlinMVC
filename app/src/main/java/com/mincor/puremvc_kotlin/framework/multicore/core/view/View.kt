package com.mincor.puremvc_kotlin.framework.multicore.core.view

import android.app.Activity
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.mincor.puremvc_kotlin.activity.log
import com.mincor.puremvc_kotlin.framework.multicore.core.animation.LinearAnimator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.*
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Observer


/**
 * Created by a.minkin on 21.11.2017.
 */
open class View private constructor(private var multitonKey: String) : IView {

    // Mapping of Mediator names to Mediator instances
    // Mapping of Notification names to Observer lists
    private val observerMap: MutableMap<String, MutableList<IObserver>> = mutableMapOf()
    private val mediatorMap: MutableMap<String, IMediator> = mutableMapOf()

    // List of current added mediators on the screen
    private val mediatorBackStack: MutableList<IMediator> = mutableListOf()
    // Current showing mediator
    private var currentShowingMediator: IMediator? = null

    /**
     * Reference to the Activity attached on core
     */
    override var currentActivity: Activity? = null
    /**
     * Instance of ui container
     */
    override var currentContainer: ViewGroup? = null


    companion object {
        private val instanceMap: MutableMap<String, View> = mutableMapOf()
        /**
         * View Singleton Factory method.
         *
         * @return the Singleton instance of `View`
         */
        @Synchronized
        fun getInstance(key: String): View = instanceMap.getOrPut(key) { View(key) }

        /**
         * Remove an IView instance
         *
         * @param key of IView instance to remove
         */
        @Synchronized
        fun removeView(key: String) {
            instanceMap.remove(key)
        }
    }

    /**
     * initialization
     *
     * <P>
     * This `IView` implementation is a Multiton,
     * so you should not call the constructor
     * directly, but instead call the static Multiton
     * Factory method `View.getInstance( multitonKey )`
    </P> */
    init {
        instanceMap.getOrPut(multitonKey) { this }
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
        this.observerMap[note.name]?.let {
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
        this.observerMap[notificationName]?.let {
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
        // Register the Mediator for retrieval by name
        val currentMediator = this.mediatorMap.getOrPut(mediator.mediatorName) { mediator }
        // Only fresh mediators can register observers
        currentMediator.multitonKey ?: let {
            currentMediator.initializeNotifier(multitonKey)
            // Get Notification interests, if any.
            val noteInterests = currentMediator.listNotificationInterests()
            if (noteInterests.isNotEmpty()) {
                // Create java style function ref to mediator.handleNotification
                val function = object : IFunction {
                    override fun onNotification(notification: INotification) {
                        currentMediator.handleNotification(notification)
                    }
                }

                // Create Observer
                val observer = Observer(function, it)

                // Register Mediator as Observer for its list of Notification
                // interests
                noteInterests.forEachIndexed { _, s ->
                    registerObserver(s, observer)
                }
            }
        }
        currentMediator.onRegister()
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
        val observers = this.observerMap.getOrPut(noteName) { mutableListOf() }
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

        mediator?.let {
            // for every notification this mediator is interested in...
            val interests = it.listNotificationInterests()
            // remove the observer linking the mediator
            // to the notification interest
            interests.forEachIndexed { _, s ->
                removeObserver(s, it)
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
    override fun retrieveMediator(mediatorName: String): IMediator = this.mediatorMap[mediatorName] as IMediator

    /**
     * Show current selected mediator
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     *
     * @param popLast
     * flag that indicates need to remove last showing from backstack
     */
    override fun showMediator(mediatorName: String, popLast: Boolean, animation:IAnimator?) {
        val lastMediator = currentShowingMediator

        // hide last mediator
        //currentShowingMediator?.hide(popLast)

        // Retrieve the named mediator
        currentShowingMediator = mediatorMap[mediatorName]
        currentShowingMediator!!.let {
            // make sure that we have an actual viewComponent if not recreate it
            it.viewComponent ?: it.onCreateView()
            // add view component to the container
            currentContainer?.addView(it.viewComponent)
            // add to backstack if we don't have any mediators in it or last mediator does not equal the same mediator as we showing on the screen
            if(mediatorBackStack.isEmpty() || mediatorBackStack.last() != it) {
                mediatorBackStack.add(it)
            }

            animation?.apply {
                from = lastMediator
                to = it
                isShow = true
                playAnimation()
            }
        }
    }

    /**
     * Show last added IMediator from backstack. If there is no mediator in backstack show the one passed by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     */
    override fun showLastOrExistMediator(mediatorName: String, animation:IAnimator?) {
        val lastMediator = mediatorBackStack.lastOrNull()
        val lastMediatorName = lastMediator?.mediatorName ?: mediatorName
        showMediator(lastMediatorName, false, animation)
    }

    /**
     * Hide current mediator by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     *
     * @param popIt
     * Indicates that is need to be removed from backstack
     */
    override fun hideMediator(mediatorName: String, popIt: Boolean, animation:IAnimator?) {
        mediatorMap[mediatorName]?.let { mediator->
            animation?.let { anim ->
                anim.apply {
                    from = mediator
                    isShow = false
                    playAnimation()
                }
            } ?: mediator.apply {
                // remove viewComponent from ui layer
                currentContainer?.removeView(viewComponent)
                // free the reference on viewComponent cause we don't need it anymore
                viewComponent = null
            }

            // if flag `true` we remove mediator from backstack on lastAddedIndex
            if (popIt) {
                mediatorBackStack.removeAt(mediatorBackStack.lastIndexOf(mediator))
            }
        }
    }

    /**
     * Hide current mediator by the name and remove it from backstack then show last added mediator at backstack
     * If there is no mediator in backstack - there is no action will be (only if backstack size > 1)
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     */
    override fun popMediator(mediatorName: String, animation:IAnimator?) {

        mediatorMap[mediatorName]?.let { mediatorToPop ->
            // if mediator to pop equal current showing mediator and backstack has more than one mediator
            if (mediatorToPop == currentShowingMediator && mediatorBackStack.size > 1) {

                // get last added mediator from backstack and show it on the screen
                val lastAddedMediator = mediatorBackStack[mediatorBackStack.lastIndexOf(mediatorToPop)-1]
                lastAddedMediator.show()

                // if we have a animation just play it
                animation?.let { anim ->
                    anim.apply {
                        from = mediatorToPop
                        to = lastAddedMediator
                        isShow = false
                        playAnimation()
                    }
                } ?: mediatorToPop.apply {
                    // hide selected mediator and remove it from backstack
                    hide(true)
                }


            }
        }
    }

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName
     * @return whether a Mediator is registered with the given `mediatorName`.
     */
    override fun hasMediator(mediatorName: String): Boolean = mediatorMap.containsKey(mediatorName)
}