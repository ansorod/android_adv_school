package com.ansorod.actionsandgroups

import android.content.Context

object PreferencesHelper {

    private const val PREFERENCES_NAME = "PREFERENCES_NAME_NOTIFICATION"
    private const val KEY_TAP_TIMESTAMP = "KEY_TAP_TIMESTAMP"

    fun storeTimestamp(context: Context, timestamp: Long) {
        val prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_TAP_TIMESTAMP, timestamp).apply()
    }

    fun getLastTapTimestamp(context: Context): Long {
        val prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_TAP_TIMESTAMP, -1)
    }
}