package com.mincor.puremvc_kotlin.framework.multicore.core.model

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IModel
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IProxy

/**
 * Created by a.minkin on 21.11.2017.
 */
open class Model(private var multitonKey: String) : IModel {
    /**
     * Mapping of proxyNames to IProxy instances.
     */
    private var proxyMap: MutableMap<String, IProxy<*>> = mutableMapOf()

    companion object {
        private var instanceMap: MutableMap<String, Model> = mutableMapOf()
        /**
         * `Model` Multiton Factory method.
         *
         * @return the instance for this Multiton key
         */
        @Synchronized
        fun getInstance(key: String): Model {
            return instanceMap.getOrPut(key){ Model(key) }
        }

        /**
         * Remove an IModel instance
         *
         * @param key of IModel instance to remove
         */
        @Synchronized
        fun removeModel(key: String) {
            instanceMap.remove(key)
        }
    }
    /**
     *
     * <P>
     * This `IModel` implementation is a Multiton
     * so you should not call the constructor
     * directly, but instead call the static Multiton
     * Factory method `Model.getInstance( multitonKey )`
     *
    </P> */
    init {
        instanceMap.getOrPut(multitonKey){this}
        initializeModel()
    }

    /**
     * Initialize the Singleton `Model` instance.
     *
     * <P>
     * Called automatically by the constructor, this is your opportunity to
     * initialize the Singleton instance in your subclass without overriding the
     * constructor.
    </P> *
     *
     */
    open fun initializeModel() {}

    /**
     * Register an `Proxy` with the `Model`.
     *
     * @param proxy
     * an `Proxy` to be held by the `Model`.
     */
    override fun registerProxy(proxy: IProxy<*>) {
        proxy.initializeNotifier(multitonKey)
        this.proxyMap.put(proxy.proxyName, proxy)
        proxy.onRegister()
    }

    /**
     * Remove an `Proxy` from the `Model`.
     *
     * @param proxyName
     * name of the `Proxy` instance to be removed.
     */
    override fun removeProxy(proxyName: String): IProxy<*> {
        val proxy = this.proxyMap[proxyName] as IProxy
        proxy.let {
            this.proxyMap.remove(proxyName)
            it.onRemove()
        }
        return proxy
    }

    /**
     * Retrieve an `Proxy` from the `Model`.
     *
     * @param proxy
     * @return the `Proxy` instance previously registered with the
     * given `proxyName`.
     */
    override fun retrieveProxy(proxy: String): IProxy<*> {
        return this.proxyMap[proxy] as IProxy
    }

    /**
     * Check if a Proxy is registered
     *
     * @param proxyName
     * @return whether a Proxy is currently registered with the given `proxyName`.
     */
    override fun hasProxy(proxyName: String): Boolean {
        return proxyMap.containsKey(proxyName)
    }
}