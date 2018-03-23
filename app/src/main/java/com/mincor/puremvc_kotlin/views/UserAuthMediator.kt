package com.mincor.puremvc_kotlin.views

import android.support.v4.content.ContextCompat
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.mincor.puremvc_kotlin.R
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.models.UserProxy
import com.mincor.puremvc_kotlin.utils.Keyboards
import com.rasalexman.kotlinmvc.core.animation.LinearAnimator
import com.rasalexman.kotlinmvc.interfaces.INotification
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onEditorAction
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

/**
 * Created by a.minkin on 22.11.2017.
 */
class UserAuthMediator : ToolbarMediator(NAME) {

    override var hasOptionalMenu: Boolean = true

    companion object {
        val NAME = "user_auth_mediator"
    }

    var name = ""
    var password = ""

    override fun onCreateView() {
        viewComponent = UserAuthUI().createView(AnkoContext.create(facade.activity, this))
        super.onCreateView()
    }

    fun nameUpdated(newName: String) {
        name = newName
    }

    private fun passwordUpdated(newPassword: String) {
        password = newPassword
    }

    private fun handleEditorAction(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            onLoginClicked()
            return true
        }
        return false
    }

    private fun onLoginClicked() {
        Keyboards.hideKeyboard(viewComponent!!.context, viewComponent!!)
        sendNotification(AppFacade.AUTH, arrayOf(name, password))
    }

    private fun onHideClicked() {
        hide(true, LinearAnimator())
    }

    override fun listNotificationInterests(): Array<String> =
            arrayOf(UserProxy.NOTIFICATION_AUTH_COMPLETE, UserProxy.NOTIFICATION_AUTH_FAILED)

    override fun handleNotification(notification: INotification) {
        when (notification.name) {
            UserProxy.NOTIFICATION_AUTH_COMPLETE -> {
                facade.retrieveMediator(UserListsMediator.NAME)?.show(false, LinearAnimator())
            }
            UserProxy.NOTIFICATION_AUTH_FAILED -> Toast.makeText(facade.activity, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_auth, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> com.mincor.puremvc_kotlin.activity.log { "SETTINGS CLICKED" }
            R.id.action_about -> com.mincor.puremvc_kotlin.activity.log { "ABOUT CLICKED" }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class UserAuthUI : AnkoComponent<UserAuthMediator> {
        override fun createView(ui: AnkoContext<UserAuthMediator>) = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)
                toolBar = toolbar {
                    setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    title = "Login"
                    backgroundResource = R.color.colorPrimary
                }

                editText(name) {
                    hint = "Username"
                    imeOptions = EditorInfo.IME_ACTION_NEXT
                    singleLine = true
                    textChangedListener {
                        onTextChanged { text, _, _, _ ->
                            nameUpdated(text.toString())
                        }
                    }
                }

                editText(password) {
                    hint = "Password"
                    singleLine = true
                    imeOptions = EditorInfo.IME_ACTION_SEND
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    textChangedListener {
                        onTextChanged { text, _, _, _ ->
                            passwordUpdated(text.toString())
                        }
                    }
                    onEditorAction { _, actionId, _ -> handleEditorAction(actionId) }
                }

                button("Login") {
                    onClick { onLoginClicked() }
                }

                button("Hide") {
                    onClick { onHideClicked() }
                }
            }
        }
    }
}