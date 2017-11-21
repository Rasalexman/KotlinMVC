package com.mincor.puremvc_kotlin.framework.multicore.patterns.observer

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification

/**
 * Created by a.minkin on 21.11.2017.
 */

/**
 * Constructor.
 *
 * @param name
 * name of the `Notification` instance. (required)
 * @param body
 * the `Notification` body. (optional)
 * @param type
 * the type of the `Notification` (optional)
 */

data class Notification(override var name: String, override var body: Any? = null, override var type: String? = null) : INotification {
    /**
     * Get the string representation of the `Notification`
     * instance.
     *
     * @return the string representation of the `Notification`
     * instance.
     */
    override fun toString(): String {
        var result = "Notification Name: $name Body:"
        result += this.body?.let { it.toString()+" Type:" } ?:  "null Type:"
        result += type?:"null"
        return result
    }
}