package com.mincor.puremvc_kotlin.models

import com.mincor.puremvc_kotlin.models.vo.UserModel
import com.rasalexman.kotlinmvc.patterns.proxy.Proxy

/**
 * Created by a.minkin on 21.11.2017.
 */
class UserProxy : Proxy<MutableList<UserModel>>(NAME, mutableListOf()) {

    companion object {
        val NAME = "UserProxy"

        val NOTIFICATION_AUTH_COMPLETE = "AUTH_COMPLETE"
        val NOTIFICATION_AUTH_FAILED = "AUTH_FAILED"
    }

    init {
        addItem(UserModel("Alex", "Minkin", 30, "rastarz@yandex.ru", "efdsghghgh"))
        addItem(UserModel("Piter", "Griffin", 44, "piter@gmail.com", "dsds457dfds1224hg"))
        addItem(UserModel("Glen", "Marson", 30, "marson@yahoo.com", "sf11GH45S555"))
    }

    /**
     * Authorization User
     */
    fun authorization(email: String, pass: String) {
        this.sendNotification(NOTIFICATION_AUTH_COMPLETE)
        return

        data.forEachIndexed { _, userModel ->
            if (userModel.email == email && userModel.password == pass) {
                this.sendNotification(NOTIFICATION_AUTH_COMPLETE)
                return
            }
        }
        this.sendNotification(NOTIFICATION_AUTH_FAILED)
    }


    /**
     * Add an item to the data.
     * @param item the userVO
     */
    fun addItem(item: UserModel) {
        data.add(item)
    }
}