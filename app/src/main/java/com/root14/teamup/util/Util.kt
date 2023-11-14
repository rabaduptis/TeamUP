package com.root14.teamup.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.root14.teamup.view.activity.MainActivity

class Util {
    companion object {
        /**
         * Sets a listener to the view that will be called when the system window insets change. This can be
         * used to ensure that the view's content is not obscured by the system bars, such as the status bar
         * and navigation bar.
         *
         * <p>The listener will be called with the new insets whenever the system window insets change. The
         * view's padding can then be set to match the insets to ensure that the view's content is not
         * obscured.
         *
         * @param view The view to set the listener on.
         */
        fun applyWindowInsets(view: View, componentActivity: ComponentActivity) {
            componentActivity.enableEdgeToEdge()
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

    }
}