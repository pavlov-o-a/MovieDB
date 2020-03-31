package com.companyname.common.preferences

import android.content.Context

class AppPreferences(private val prefsName: String) {

    private val cache = mutableMapOf<String, Any?>()

    private inline fun <reified T> getFromCache(key: String): T?{
        val fromCache = cache[key]
        if (fromCache != null && fromCache is T)
            return fromCache
        return null
    }

    fun saveString(context: Context, value: String, key: String){
        cache[key] = value
        context
            .getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getString(context: Context, key: String): String?{
        val cached = getFromCache<String>(key)
        cached?.let { return it }
        val fromPrefs = context
            .getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .getString(key, null)
        cache[key] = fromPrefs
        return fromPrefs
    }

    fun saveBoolean(context: Context, value: Boolean, key: String){
        cache[key] = value
        context
            .getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getBoolean(context: Context, key: String): Boolean{
        val cached = getFromCache<Boolean>(key)
        cached?.let { return it }
        val fromPrefs = context
            .getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .getBoolean(key, false)
        cache[key] = fromPrefs
        return fromPrefs
    }
}