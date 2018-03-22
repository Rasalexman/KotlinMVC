package com.mincor.puremvc_kotlin.views

import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.mincor.puremvc_kotlin.R
import com.rasalexman.kotlinmvc.core.animation.LinearAnimator
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by a.minkin on 22.11.2017.
 */
class UserListsMediator : ToolbarMediator(NAME) {

    override var hasOptionalMenu: Boolean = true

    companion object {
        val NAME = "user_list_mediator"
    }

    override fun onCreateView() {
        viewComponent = UserListUI().createView(AnkoContext.Companion.create(getFacade().activity, this))
        super.onCreateView()
        setHomeButtonEnable()
    }

    fun onBackClickHandler() {
        popToBack(LinearAnimator())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list, menu)
    }

    inner class UserListUI : AnkoComponent<UserListsMediator> {
        override fun createView(ui: AnkoContext<UserListsMediator>): View = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)

                toolBar = toolbar {
                    setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    title = "User List"
                    backgroundResource = R.color.colorPrimary
                }

                button("back") {
                    onClick {
                        onBackClickHandler()
                    }
                }

                recyclerView {

                }
            }
        }
    }
}