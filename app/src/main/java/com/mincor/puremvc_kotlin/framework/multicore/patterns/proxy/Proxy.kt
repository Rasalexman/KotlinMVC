package com.mincor.puremvc_kotlin.framework.multicore.patterns.proxy

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IProxy
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notifier

/**
 * Created by a.minkin on 21.11.2017.
 */
open class Proxy(override var proxyName:String = "Proxy", override var data: Any? = null) : Notifier(), IProxy {
    /**
     * Called by the Model when the Proxy is registered.
     */
    override fun onRegister() {}

    /**
     * Called by the Model when the Proxy is removed.
     */
    override fun onRemove() {}
}