package com.companyname.catalog.misc

import android.content.Context
import com.companyname.common.preferences.AppPreferences

object CatalogSettings {
    private const val SHOW_IMGS = "SHOW_IMGS"
    private val settings = AppPreferences("CatalogSettings")

    fun shouldShowImages(context: Context) = settings.getBoolean(context, SHOW_IMGS)
    fun setShowImages(context: Context, value: Boolean) = settings.saveBoolean(context, value, SHOW_IMGS)
}