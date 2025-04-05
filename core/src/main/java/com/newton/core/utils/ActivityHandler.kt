package com.newton.core.utils

import android.app.*

/**
 * Utility class to handle activity control operations like exiting app
 */
object ActivityHandler {
    /**
     * Exits the app by finishing all activities in the task
     * @param activity The activity context to finish
     */
    fun exitApp(activity: Activity) {
        activity.finishAffinity()
    }
}
