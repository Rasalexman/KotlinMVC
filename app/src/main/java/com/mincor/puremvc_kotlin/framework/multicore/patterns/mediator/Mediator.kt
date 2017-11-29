package com.mincor.puremvc_kotlin.framework.multicore.patterns.mediator

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IAnimator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.IMediator
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notifier


/**
 * Created by a.minkin on 21.11.2017.
 */
abstract class Mediator(override val mediatorName: String) : Notifier(), IMediator {

    override var viewComponent: View? = null
    override var hasOptionalMenu:Boolean = false


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {}
    override fun onPrepareOptionsMenu(menu: Menu) {}
    override fun onOptionsItemSelected(item: MenuItem): Boolean = true

    /**
     * Handle `INotification`s.
     *
     * <P>
     * Typically this will be handled in a switch statement, with one 'case'
     * entry per `INotification` the `Mediator` is
     * interested in.
     * @param notification
    </P> */
    override fun handleNotification(notification: INotification) {}

    /**
     * List the `INotification` names this `Mediator`
     * is interested in being notified of.
     *
     * @return String[] the list of `INotification` names
     */
    override fun listNotificationInterests(): Array<String> = arrayOf()

    /**
     * Called by the View when the Mediator is registered.
     */
    override fun onRegister() {}

    /**
     * Called by the View when the Mediator is removed.
     */
    override fun onRemove() {

    }

    /**
     * Add current mediator to view stage
     */
    override fun show(popLast: Boolean, animation:IAnimator?) {
        getFacade().showMeditator(mediatorName, popLast, animation)
    }

    /**
     * Remove current mediator from view stage
     */
    override fun hide(popIt: Boolean, animation:IAnimator?) {
        getFacade().hideMediator(mediatorName, popIt, animation)
    }

    /**
     * Remove current mediator from view stage and backstack
     * Then show the last added to backstack mediator on the view stage
     */
    override fun popToBack(animation:IAnimator?) {
        getFacade().popMediator(mediatorName, animation)
    }
}