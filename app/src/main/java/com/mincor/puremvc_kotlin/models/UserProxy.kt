package com.mincor.puremvc_kotlin.models

import com.mincor.puremvc_kotlin.framework.multicore.patterns.proxy.Proxy
import com.mincor.puremvc_kotlin.models.vo.UserModel

/**
 * Created by a.minkin on 21.11.2017.
 */
class UserProxy : Proxy<UserModel>(NAME, mutableListOf()) {

    companion object {
        val NAME = "UserProxy"
    }

    init {
        addItem(UserModel("Alex", "Minkin", 30, "sphc@yandex.ru", "030287"))
        addItem(UserModel("Piter", "Griffin", 44, "piter@gmail.com", "dsds457dfds1224hg"))
        addItem(UserModel("Glen", "Marson", 30, "marson@yahoo.com", "sf11GH45S555"))
    }

    /**
     * Authorization User
     */
    fun authorization(email:String, pass:String){
        data.forEach {
            if(it.email == email && it.password == pass){

                //this.sendNotification("AUTH_COMPLETE")
            }
        }
    }

    /**
     * Add an item to the data.
     * @param item the userVO
     */
    fun addItem(item: UserModel) {
        data.add(item)
    }
}