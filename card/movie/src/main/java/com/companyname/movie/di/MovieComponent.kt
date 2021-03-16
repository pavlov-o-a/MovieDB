package com.companyname.movie.di

import com.companyname.movie.presentation.view.MovieCardFragment
import com.companyname.moviedb.di.AppComponent
import com.companyname.repository.net.NetworkModule
import dagger.Component

@MovieScope
@Component(dependencies = [AppComponent::class], modules = [MovieModule::class])
interface MovieComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MovieComponent
    }

    fun inject(fragment: MovieCardFragment)
}