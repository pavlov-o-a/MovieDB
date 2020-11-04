package com.companyname.catalog.di

import com.companyname.catalog.presentation.view.CatalogFragment
import com.companyname.moviedb.di.AppComponent
import dagger.Component

@CatalogScope
@Component(dependencies = [AppComponent::class], modules = [CatalogModule::class])
interface CatalogComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CatalogComponent
    }

    fun inject(fragment: CatalogFragment)
}