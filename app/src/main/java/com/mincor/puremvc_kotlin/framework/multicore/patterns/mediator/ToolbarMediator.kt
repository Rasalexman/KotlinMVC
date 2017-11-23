package com.mincor.puremvc_kotlin.framework.multicore.patterns.mediator

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.mincor.puremvc_kotlin.activity.log
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.common.IActionBarProvider

/**
 * Created by a.minkin on 23.11.2017.
 */
abstract class ToolbarMediator(override val mediatorName: String) : Mediator(mediatorName), View.OnClickListener {

    companion object {
        val BACK_BUTTON_ID = -1
    }

    protected var toolBar: Toolbar? = null

    override fun hide(popIt: Boolean) {
        super.hide(popIt)
        toolBar?.setOnClickListener(null)
        (getFacade().activity as IActionBarProvider).setSupportActionBar(null)
        toolBar = null
    }

    private val actionBar: ActionBar?
        get() {
            val actionBarProvider = (getFacade().activity as IActionBarProvider)
            return actionBarProvider.getSupportActionBar()
        }
    protected fun setActionBar(toolbar: Toolbar?) {
        toolbar?.let{
            (getFacade().activity as IActionBarProvider).setSupportActionBar(it)
            it.setNavigationOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        log {"VIEW ID ${view.id}"}
    }

    protected fun setHomeButtonEnable() {
        //set the back arrow in the toolbar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}