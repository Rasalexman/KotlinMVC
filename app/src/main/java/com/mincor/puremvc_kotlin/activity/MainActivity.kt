package com.mincor.puremvc_kotlin.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.mincor.puremvc_kotlin.BuildConfig
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.views.UserAuthMediator
import com.rasalexman.kotlinmvc.core.animation.LinearAnimator
import com.rasalexman.kotlinmvc.interfaces.common.IActionBarProvider
import com.rasalexman.kotlinmvc.patterns.facade.Facade

class MainActivity : AppCompatActivity(), IActionBarProvider<ActionBar, Toolbar> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val container = frameLayout { lparams(matchParent, matchParent) }
        val appFacade: AppFacade = Facade.getInstance(AppFacade.NAME) as AppFacade
        appFacade.attachActivity(this).showLastOrExistMediator(UserAuthMediator.NAME, LinearAnimator())
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        toolbar?.let {
            super.setSupportActionBar(toolbar)
        }
    }
}

inline fun log(lambda: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("KOTLIN_TAG", lambda())
    }
}
