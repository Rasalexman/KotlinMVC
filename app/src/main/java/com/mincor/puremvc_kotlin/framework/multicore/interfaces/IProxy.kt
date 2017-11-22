package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IProxy<out T> : INotifier {

    val proxyName:String
    val data:T
    /**
     * Called by the Model when the Proxy is registered.
     */
    fun onRegister()

    /**
     * Called by the Model when the Proxy is removed.
     */
    fun onRemove()
}