package com.mincor.puremvc_kotlin.framework.multicore.interfaces.common

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar


interface IActionBarProvider {
    fun getSupportActionBar():ActionBar?
    fun setSupportActionBar(toolbar: Toolbar?)
}
