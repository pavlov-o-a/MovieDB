package com.companyname.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.companyname.catalog.presentation.view.CatalogFragment

class AppActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
    }
}