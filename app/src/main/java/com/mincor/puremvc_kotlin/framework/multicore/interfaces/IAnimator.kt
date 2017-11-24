package com.mincor.puremvc_kotlin.framework.multicore.interfaces

import android.animation.Animator

/**
 * Created by a.minkin on 24.11.2017.
 */
interface IAnimator {
    var to:IMediator?
    var from:IMediator?
    var duration: Long
    var isShow:Boolean

    fun getAnimator():Animator
    fun playAnimation()
}