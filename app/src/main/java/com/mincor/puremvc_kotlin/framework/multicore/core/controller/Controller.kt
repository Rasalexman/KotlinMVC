package com.mincor.puremvc_kotlin.framework.multicore.core.controller

import com.mincor.puremvc_kotlin.framework.multicore.core.view.View
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IController
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IFunction
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.ICommand
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Observer

open class Controller(private val multitonKey: String) : IController {

    /**
     * Mapping of Notification names to Command Class references
     */
    private var commandMap: MutableMap<String, ICommand> = mutableMapOf()

    /**
     * Local reference to View
     */
    protected var view: View

    companion object {
        private val instanceMap: MutableMap<String, Controller> = HashMap()
        /**
         * `Controller` Multiton Factory method.
         * @return the Multiton instance of `Controller`
         */
        @Synchronized
        fun getInstance(key: String): Controller {
            return instanceMap.getOrPut(key){Controller(key)}
        }
        /**
         * Remove an IController instance
         * @param key of IController instance to remove
         */
        @Synchronized
        fun removeController(key: String) {
            instanceMap.remove(key)
        }
    }

    init {
        instanceMap.getOrPut(multitonKey) { this }
        this.view = View.getInstance(multitonKey)
    }

    /**
     * If an `ICommand` has previously been registered to handle a
     * the given `INotification`, then it is executed.
     *
     * @param notification
     * an `INotification`
     */
    override fun executeCommand(notification: INotification) {
        val commandInstance = this.commandMap[notification.name] as ICommand
        commandInstance.let {
            it.initializeNotifier(multitonKey)
            it.execute(notification)
        }
    }

    /**
     * Register a particular `ICommand` class as the handler for a
     * particular `INotification`.
     *
     * <P>
     * If an `ICommand` has already been registered to handle
     * `INotification`s with this name, it is no longer used, the
     * new `ICommand` is used instead.
    </P> *
     *
     * The Observer for the new ICommand is only created if this the
     * first time an ICommand has been regisered for this Notification name.
     *
     * @param notificationName
     * the name of the `INotification`
     * @param command
     * an instance of `ICommand`
     */
    override fun registerCommand(notificationName: String, command: ICommand) {
        if (this.commandMap.containsKey(notificationName)) {
            return
        }
        this.commandMap.getOrPut(notificationName) { command }
        this.view.registerObserver(notificationName, Observer(object : IFunction {
            override fun onNotification(notification: INotification) {
                executeCommand(notification)
            }
        }, this))
    }

    /**
     * Remove a previously registered `ICommand` to
     * `INotification` mapping.
     *
     * @param notificationName
     * the name of the `INotification` to remove the
     * `ICommand` mapping for
     */
    override fun removeCommand(notificationName: String) {
        // if the Command is registered...
        if (hasCommand(notificationName)) {
            // remove the observer
            view.removeObserver(notificationName, this)
            this.commandMap.remove(notificationName)
        }
    }

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName
     * @return whether a Command is currently registered for the given `notificationName`.
     */
    override fun hasCommand(notificationName: String): Boolean {
        return commandMap[notificationName] != null
    }
}