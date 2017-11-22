package com.mincor.puremvc_kotlin.views

import android.support.v4.content.ContextCompat
import android.view.View
import com.mincor.puremvc_kotlin.R
import com.mincor.puremvc_kotlin.framework.multicore.patterns.mediator.Mediator
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by a.minkin on 22.11.2017.
 */
class UserListsMediator : Mediator(NAME) {

    companion object {
        val NAME = "user_list_mediator"
    }

    override fun onCreateView() {
        viewComponent = UserListUI().createView(AnkoContext.Companion.create(getFacade().activity, this))
    }

    inner class UserListUI : AnkoComponent<UserListsMediator> {
        override fun createView(ui: AnkoContext<UserListsMediator>): View = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)

                toolbar {
                    setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    title = "User List"
                    backgroundResource = R.color.colorPrimary
                }

                recyclerView {

                }
            }
        }
    }
}