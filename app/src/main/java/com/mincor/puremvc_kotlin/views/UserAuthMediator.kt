package com.mincor.puremvc_kotlin.views

import android.support.v4.content.ContextCompat
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.mincor.puremvc_kotlin.R
import com.mincor.puremvc_kotlin.facades.AppFacade
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.mediator.Mediator
import com.mincor.puremvc_kotlin.models.UserProxy
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onEditorAction
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

/**
 * Created by a.minkin on 22.11.2017.
 */
class UserAuthMediator(private val container: ViewGroup) : Mediator(NAME) {

    override val viewComponent: View? = UserAuthUI().createView(AnkoContext.create(container.context, this))

    companion object {
        val NAME = "user_auth_mediator"
    }

    var name = ""
    var password = ""

    override fun onRegister() {
        container.addView(viewComponent)
    }

    override fun onRemove() {
        container.removeView(viewComponent)
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
       sendNotification(AppFacade.AUTH, arrayOf(name, password))
    }

    override fun listNotificationInterests(): Array<String> {
        return arrayOf(UserProxy.NOTIFICATION_AUTH_COMPLETE, UserProxy.NOTIFICATION_AUTH_FAILED)
    }

    override fun handleNotification(notification: INotification) {
        when(notification.name){
            UserProxy.NOTIFICATION_AUTH_COMPLETE -> {
                getFacade().removeMediator(NAME)
                getFacade().registerMediator(UserListsMediator(container))
            }
            UserProxy.NOTIFICATION_AUTH_FAILED -> Toast.makeText(viewComponent?.context, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

    inner class UserAuthUI : AnkoComponent<UserAuthMediator>{
        override fun createView(ui: AnkoContext<UserAuthMediator>) = with(ui){
            verticalLayout {
                lparams(matchParent, matchParent)
                toolbar {
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
            }
        }
    }
}