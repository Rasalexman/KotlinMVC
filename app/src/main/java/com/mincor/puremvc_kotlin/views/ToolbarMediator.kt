package com.mincor.puremvc_kotlin.views

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.mincor.puremvc_kotlin.activity.log
import com.rasalexman.kotlinmvc.core.animation.LinearAnimator
import com.rasalexman.kotlinmvc.interfaces.common.IActionBarProvider
import com.rasalexman.kotlinmvc.patterns.mediator.Mediator

/**
 * Created by a.minkin on 23.11.2017.
 */
abstract class ToolbarMediator(override val mediatorName: String) : Mediator(mediatorName), View.OnClickListener {

    companion object {
        val BACK_BUTTON_ID = -1
    }

    protected var toolBar: Toolbar? = null

    override fun onCreateView() {
        toolBar?.let {
            setActionBar(it)
        }
    }

    override fun onRemove() {
        super.onRemove()
        toolBar?.setNavigationOnClickListener(null)
        (facade.activity as IActionBarProvider<ActionBar, Toolbar>).setSupportActionBar(null)
        toolBar = null
    }

    private val actionBar: ActionBar?
        get() {
            val actionBarProvider = (facade.activity as IActionBarProvider<ActionBar, Toolbar>)
            return actionBarProvider.getSupportActionBar()
        }

    protected fun setActionBar(toolbar: Toolbar?) {
        toolbar?.let {
            (facade.activity as IActionBarProvider<ActionBar, Toolbar>).setSupportActionBar(it)
            it.setNavigationOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        log { "VIEW ID ${view.id}" }
        when (view.id) {
            BACK_BUTTON_ID -> popToBack(LinearAnimator())
        }
    }

    protected fun setHomeButtonEnable() {
        //set the back arrow in the toolbar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}