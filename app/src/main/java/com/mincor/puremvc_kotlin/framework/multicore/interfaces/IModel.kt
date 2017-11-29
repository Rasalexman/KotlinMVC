package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IModel {
    /**
     * Register an `IProxy` instance with the `Model`.
     *
     * @param proxy
     * an object reference to be held by the `Model`.
     */
    fun registerProxy(proxy: IProxy<*>)

    /**
     * Retrieve an `IProxy` instance from the Model.
     *
     * @param proxyName
     * @return the `IProxy` instance previously registered with the
     * given `proxyName`.
     */
    fun retrieveProxy(proxyName: String): IProxy<*>

    /**
     * Remove an `IProxy` instance from the Model.
     *
     * @param proxyName
     * name of the `IProxy` instance to be removed.
     */
    fun removeProxy(proxyName: String): IProxy<*>

    /**
     * Check if a Proxy is registered.
     *
     * @param proxyName
     * @return whether a Proxy is currently registered with the given `proxyName`.
     */
    fun hasProxy(proxyName: String): Boolean
}