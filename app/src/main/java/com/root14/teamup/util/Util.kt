package com.root14.teamup.util

import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
         * Example usage:
         *
         * ```
         * Util.applyWindowInsets(binding.root, this)
         *
         * ```
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

        /**
         * Adjusts the view hierarchy to consume window insets. This ensures that the view and its
         * children will not be clipped by system UI elements such as the status bar and navigation bar.
         *
         * @param rootView The root view of the view hierarchy.
         */
        fun adjustViewForWindowInsets(rootView: View) {
            // Set a listener on the decor view to apply window insets when they change
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
                // Consume the window insets so that they are not applied to child views
                ViewCompat.onApplyWindowInsets(rootView, WindowInsetsCompat.CONSUMED)
            }
        }

    }
}