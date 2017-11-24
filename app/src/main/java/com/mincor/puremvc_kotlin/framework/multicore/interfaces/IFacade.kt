package com.mincor.puremvc_kotlin.framework.multicore.interfaces

import android.app.Activity
import android.view.ViewGroup

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IFacade : INotifier {

    /***
     * Attach main Activity class to current Facade Core
     * @param activity - activity for attachment and lifecyrcle handled
     * @param container - container for add/remove ui
     */
    fun attachActivity(activity: Activity, container: ViewGroup)


    /**
     * Register an `IProxy` with the `Model` by name.
     *
     * @param proxy
     * the `IProxy` to be registered with the
     * `Model`.
     */
    fun registerProxy(proxy: IProxy<*>)

    /**
     * Retrieve a `IProxy` from the `Model` by name.
     *
     * @param proxyName
     * the name of the `IProxy` instance to be
     * retrieved.
     * @return the `IProxy` previously regisetered by
     * `proxyName` with the `Model`.
     */
    fun retrieveProxy(proxyName: String): IProxy<*>?

    /**
     * Remove an `IProxy` instance from the `Model` by
     * name.
     *
     * @param proxyName
     * the `IProxy` to remove from the
     * `Model`.
     */
    fun removeProxy(proxyName: String): IProxy<*>

    /**
     * Check if a Proxy is registered.
     *
     * @param proxyName
     * @return whether a Proxy is currently registered with the given `proxyName`.
     */
    fun hasProxy(proxyName: String): Boolean

    /**
     * Register an `ICommand` with the `Controller`.
     *
     * @param noteName
     * the name of the `INotification` to associate the
     * `ICommand` with.
     * @param commandClassRef
     * a reference to the `Class` of the
     * `ICommand`.
     */
    fun registerCommand(noteName: String, commandClassRef: ICommand)

    /**
     * Remove a previously registered `ICommand` to `INotification` mapping from the Controller.
     *
     * @param notificationName the name of the `INotification` to remove the `ICommand` mapping for
     */
    fun removeCommand(notificationName: String)

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName
     * @return whether a Command is currently registered for the given `notificationName`.
     */
    fun hasCommand(notificationName: String): Boolean

    /**
     * Register an `IMediator` instance with the `View`.
     *
     * @param mediator
     * a reference to the `IMediator` instance
     */
    fun registerMediator(mediator: IMediator)

    /**
     * Retrieve an `IMediator` instance from the `View`.
     *
     * @param mediatorName
     * the name of the `IMediator` instance to retrievve
     * @return the `IMediator` previously registered with the given
     * `mediatorName`.
     */
    fun retrieveMediator(mediatorName: String): IMediator?

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName
     * @return whether a Mediator is registered with the given `mediatorName`.
     */
    fun hasMediator(mediatorName: String): Boolean

    /**
     * Remove a `IMediator` instance from the `View`.
     *
     * @param mediatorName
     * name of the `IMediator` instance to be removed.
     */
    fun removeMediator(mediatorName: String): IMediator?

    /**
     * Show current selected mediator
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     *
     * @param popLast
     * flag that indicates need to remove last showing from backstack
     */
    fun showMeditator(mediatorName: String, popLast: Boolean = false, animation:IAnimator? = null)

    /**
     * Show last added IMediator from backstack. If there is no mediator in backstack show the one passed by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to show on the screen
     */
    fun showLastOrExistMediator(mediatorName: String, animation:IAnimator? = null)

    /**
     * Hide current mediator by name
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     *
     * @param popIt
     * Indicates that is need to be removed from backstack
     */
    fun hideMediator(mediatorName: String, popIt: Boolean = false, animation:IAnimator? = null)

    /**
     * Hide current mediator by the name and remove it from backstack then show last added mediator at backstack
     * If there is no mediator in backstack there is no action will be (only if bacstack size > 1)
     *
     * @param mediatorName
     * the name of the `IMediator` instance to be removed from the screen
     */
    fun popMediator(mediatorName: String, animation:IAnimator? = null)
}