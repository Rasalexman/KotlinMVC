package com.mincor.puremvc_kotlin.framework.multicore.patterns.proxy

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IProxy
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notifier

/**
 * Created by a.minkin on 21.11.2017.
 */
open class Proxy<T:Any>(override val proxyName:String = "Proxy", override val data: MutableCollection<T>) : Notifier(), IProxy {
    /**
     * Called by the Model when the Proxy is registered.
     */
    override fun onRegister() {}

    /**
     * Called by the Model when the Proxy is removed.
     */
    override fun onRemove() {}
}