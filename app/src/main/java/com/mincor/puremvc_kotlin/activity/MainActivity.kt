package com.mincor.puremvc_kotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.mincor.puremvc_kotlin.BuildConfig
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.framework.multicore.core.animation.LinearAnimator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.common.IActionBarProvider
import com.mincor.puremvc_kotlin.framework.multicore.patterns.facade.Facade
import com.mincor.puremvc_kotlin.views.UserAuthMediator
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity(), IActionBarProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = frameLayout { lparams(matchParent, matchParent) }
        val appFacade:AppFacade = Facade.getInstance(AppFacade.NAME) as AppFacade
        appFacade.attachActivity(this, container)
        appFacade.showLastOrExistMediator(UserAuthMediator.NAME, LinearAnimator())
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
