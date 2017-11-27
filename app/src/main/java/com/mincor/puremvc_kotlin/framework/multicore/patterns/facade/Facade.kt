package com.mincor.puremvc_kotlin.framework.multicore.patterns.facade

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.ViewGroup
import com.mincor.puremvc_kotlin.framework.multicore.core.controller.Controller
import com.mincor.puremvc_kotlin.framework.multicore.core.model.Model
import com.mincor.puremvc_kotlin.framework.multicore.core.view.View
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.*
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notification


/**
 * Created by a.minkin on 21.11.2017.
 */
open class Facade(override var multitonKey: String? = DEFAULT_KEY) : IFacade {

    val container: ViewGroup get() = view.currentContainer!!
    val activity: Activity get() = view.currentActivity!!

    /**
     * Reference to the Controller
     */
    private lateinit var controller: IController

    /**
     * Reference to the Model
     */
    private lateinit var model: IModel

    /**
     * Reference to the View
     */
    private lateinit var view: IView


    companion object {
        val DEFAULT_KEY = "FACADE"

        val ACTIVITY_CREATED = "created"
        val ACTIVITY_STARTED = "started"
        val ACTIVITY_RESUMED = "resumed"
        val ACTIVITY_PAUSED = "paused"
        val ACTIVITY_STOPPED = "stopped"
        val ACTIVITY_DESTROYED = "destroyed"

        private val instanceMap: MutableMap<String, Facade> = mutableMapOf()

        /**
         * Facade Multiton Factory method.
         *
         * @return the Multiton instance of the Facade
         */
        @Synchronized
        fun getInstance(key: String): Facade = instanceMap.getOrPut(key) { Facade(key) }

        /**
         * Check if a Core is registered or not.
         *
         * @param key the multiton key for the Core in question
         * @return whether a Core is registered with the given `key`.
         */
        @Synchronized
        fun hasCore(key: String): Boolean = instanceMap.containsKey(key)

        /**
         * Remove a Core
         * @param key of the Core to remove
         */
        @Synchronized
        fun removeCore(key: String) {
            // remove the model, view, controller
            // and facade instances for this key
            Model.removeModel(key)
            View.removeView(key)
            Controller.removeController(key)
            instanceMap.remove(key)
        }
    }

    init {
        if (instanceMap[multitonKey] != null) throw RuntimeException(multitonKey + " Facade already constructed")
        instanceMap.put(multitonKey!!, this)
        initializeFacade()
    }

    /**
     * Initialize the Multiton `Facade` instance.
     *
     * <P>
     * Called automatically by the constructor. Override in your
     * subclass to do any subclass specific initializations. Be
     * sure to call `super.initializeFacade()`, though.</P>
     */
    private fun initializeFacade() {
        initializeModel()
        initializeController()
        initializeView()
    }


    /**
     * Attach current instance of Activity to Facade core
     * Only one core can has one attached activity
     */
    override fun attachActivity(activity: Activity, container: ViewGroup) {
        // attached container for UI
        view.currentContainer = container
        // current activity for support and creating UI
        view.currentActivity ?: let {
            activity.application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                    print("onActivityCreated")
                    sendNotification(ACTIVITY_CREATED)
                }

                override fun onActivityStarted(p0: Activity?) {
                    print("onActivityStarted")
                    sendNotification(ACTIVITY_STARTED)
                }

                override fun onActivityResumed(p0: Activity?) {
                    print("onActivityResumed")
                    sendNotification(ACTIVITY_RESUMED)
                }

                override fun onActivityPaused(p0: Activity?) {
                    print("onActivityPaused")
                    sendNotification(ACTIVITY_PAUSED)
                }

                override fun onActivityStopped(p0: Activity?) {
                    print("onActivityStopped")
                    sendNotification(ACTIVITY_STOPPED)
                }

                override fun onActivityDestroyed(p0: Activity?) {
                    print("onActivityDestroyed")
                    sendNotification(ACTIVITY_DESTROYED)
                    // we dont need current container anymore cause our activity is destroyed
                    view.currentContainer?.removeAllViews()
                    // clear reference
                    view.currentContainer = null
                }

                override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

                }
            })

            view.currentActivity = activity
        }
    }

    /**
     * Initialize the `Controller`.
     *
     * <P>
     * Called by the `initializeFacade` method. Override this
     * method in your subclass of `Facade` if one or both of the
     * following are true:
    </P> * <UL>
     * <LI> You wish to initialize a different `IController`.</LI>
     * <LI> You have `Commands` to register with the
     * `Controller` at startup.. </LI>
    </UL> *
     * If you don't want to initialize a different `IController`,
     * call `super.initializeController()` at the beginning of your
     * method, then register `Command`s.
     *
     */
    protected open fun initializeController() {
        this.controller = Controller.getInstance(multitonKey!!)
    }

    /**
     * Initialize the `Model`.
     *
     * <P>
     * Called by the `initializeFacade` method. Override this
     * method in your subclass of `Facade` if one or both of the
     * following are true:
    </P> * <UL>
     * <LI> You wish to initialize a different `IModel`.</LI>
     * <LI> You have `Proxy`s to register with the Model that do
     * not retrieve a reference to the Facade at construction time.</LI>
    </UL> *
     * If you don't want to initialize a different `IModel`, call
     * `super.initializeModel()` at the beginning of your method,
     * then register `Proxy`s.
     * <P>
     * Note: This method is *rarely* overridden; in practice you are more
     * likely to use a `Command` to create and register `Proxy`s
     * with the `Model`, since `Proxy`s with mutable
     * data will likely need to send `INotification`s and thus
     * will likely want to fetch a reference to the `Facade` during
     * their construction.
    </P> *
     */
    protected open fun initializeModel() {
        this.model = Model.getInstance(multitonKey!!)
    }

    /**
     * Initialize the `View`.
     *
     * <P>
     * Called by the `initializeFacade` method. Override this
     * method in your subclass of `Facade` if one or both of the
     * following are true:
    </P> * <UL>
     * <LI> You wish to initialize a different `IView`.</LI>
     * <LI> You have `Observers` to register with the
     * `View`</LI>
    </UL> *
     * If you don't want to initialize a different `IView`, call
     * `super.initializeView()` at the beginning of your method,
     * then register `IMediator` instances.
     * <P>
     * Note: This method is *rarely* overridden; in practice you are more
     * likely to use a `Command` to create and register
     * `Mediator`s with the `View`, since
     * `IMediator` instances will need to send
     * `INotification`s and thus will likely want to fetch a
     * reference to the `Facade` during their construction.
    </P> *
     */
    protected open fun initializeView() {
        this.view = View.getInstance(multitonKey!!)
    }

    /**
     * Register an `ICommand` with the `Controller` by
     * Notification name.
     *
     * @param noteName
     * the name of the `INotification` to associate the
     * `ICommand` with
     * @param commandClassRef
     * an instance of the `ICommand`
     */
    override fun registerCommand(noteName: String, commandClassRef: ICommand) {
        this.controller.registerCommand(noteName, commandClassRef)
    }

    /**
     * Remove a previously registered `ICommand` to `INotification` mapping from the Controller.
     *
     * @param notificationName the name of the `INotification` to remove the `ICommand` mapping for
     */
    override fun removeCommand(notificationName: String) {
        this.controller.removeCommand(notificationName)
    }

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName
     * @return whether a Command is currently registered for the given `notificationName`.
     */
    override fun hasCommand(notificationName: String): Boolean = controller.hasCommand(notificationName)

    /**
     * Register a `IMediator` with the `View`.
     *
     * @param mediator
     * the name to associate with this `IMediator`
     */
    override fun registerMediator(mediator: IMediator) {
        this.view.registerMediator(mediator)
    }

    /**
     * Register an `IProxy` with the `Model` by name.
     *
     * @param proxy
     * the name of the `IProxy` instance to be
     * registered with the `Model`.
     */
    override fun registerProxy(proxy: IProxy<*>) {
        this.model.registerProxy(proxy)
    }

    /**
     * Remove an `IMediator` from the `View`.
     *
     * @param mediatorName
     * name of the `IMediator` to be removed.
     * @return the `IMediator` that was removed from the `View`
     */
    override fun removeMediator(mediatorName: String): IMediator? = this.view.removeMediator(mediatorName)

    /**
     * Show current selected mediator
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     *
     * @param popLast
     * flag that indicates need to remove last showing from backstack
     *
     * @param animation
     * Instance of current animation
     */
    override fun showMeditator(mediatorName: String, popLast: Boolean, animation:IAnimator?) {
        view.showMediator(mediatorName, popLast, animation)
    }

    /**
     * Show last added IMediator from backstack. If there is no mediator in backstack show the one passed by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     */
    override fun showLastOrExistMediator(mediatorName: String, animation:IAnimator?) {
        view.showLastOrExistMediator(mediatorName, animation)
    }

    /**
     * Hide current mediator by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     *
     * @param popIt
     * Indicates that is need to be removed from backstack
     *
     * @param animation
     * Instance of current animation
     */
    override fun hideMediator(mediatorName: String, popIt: Boolean, animation:IAnimator?) {
        view.hideMediator(mediatorName, popIt, animation)
    }

    /**
     * Hide current mediator by the name and remove it from backstack then show last added mediator at backstack
     * If there is no mediator in backstack - there is no action will be (only if backstack size > 1)
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     *
     * @param animation
     * Instance of current animation
     */
    override fun popMediator(mediatorName: String, animation:IAnimator?) {
        view.popMediator(mediatorName, animation)
    }

    /**
     * Remove an `IProxy` from the `Model` by name.
     *
     * @param proxyName
     * the `IProxy` to remove from the
     * `Model`.
     * @return the `IProxy` that was removed from the `Model`
     */
    override fun removeProxy(proxyName: String): IProxy<*> = this.model.removeProxy(proxyName)

    /**
     * Check if a Proxy is registered.
     *
     * @param proxyName
     * @return whether a Proxy is currently registered with the given `proxyName`.
     */
    override fun hasProxy(proxyName: String): Boolean = model.hasProxy(proxyName)

    /**
     * Check if a Mediator is registered or not.
     *
     * @param mediatorName
     * @return whether a Mediator is registered with the given `mediatorName`.
     */
    override fun hasMediator(mediatorName: String): Boolean = view.hasMediator(mediatorName)

    /**
     * Retrieve an `IMediator` from the `View`.
     *
     * @param mediatorName
     * @return the `IMediator` previously registered with the given
     * `mediatorName`.
     */
    override fun retrieveMediator(mediatorName: String): IMediator? = this.view.retrieveMediator(mediatorName)

    /**
     * Retrieve an `IProxy` from the `Model` by name.
     *
     * @param proxyName
     * the name of the proxy to be retrieved.
     * @return the `IProxy` instance previously registered with the
     * given `proxyName`.
     */
    override fun retrieveProxy(proxyName: String): IProxy<*>? = this.model.retrieveProxy(proxyName)

    /**
     * Create and send an `INotification`.
     *
     * <P>
     * Keeps us from having to construct new notification
     * instances in our implementation code.
     * @param notificationName the name of the notification to send
     * @param body the body of the notification (optional)
     * @param type the type of the notification (optional)
    </P>
     */
    override fun sendNotification(notificationName: String, body: Any?, type: String?) {
        notifyObservers(Notification(notificationName, body, type))
    }

    /**
     * Notify `Observer`s of an `INotification`.
     *
     * @param note
     * the `INotification` to have the `View`
     * notify observers of.
     */
    private fun notifyObservers(notification: INotification) {
        this.view.notifyObservers(notification)
    }

    override fun initializeNotifier(key: String) {}
}