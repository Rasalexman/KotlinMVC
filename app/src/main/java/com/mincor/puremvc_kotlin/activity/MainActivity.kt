package com.mincor.puremvc_kotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.framework.multicore.patterns.facade.Facade
import com.mincor.puremvc_kotlin.views.UserAuthMediator
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = linearLayout {lparams(matchParent, matchParent)}
        val appFacade:AppFacade = Facade.getInstance(AppFacade.NAME) as AppFacade
        appFacade.attachActivity(this, container)
        appFacade.retrieveMediator(UserAuthMediator.NAME)?.show()
    }
}
