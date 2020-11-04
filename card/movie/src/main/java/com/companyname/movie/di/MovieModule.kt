package com.companyname.movie.di

import com.companyname.movie.logic.Logic
import com.companyname.movie.logic.LogicImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieModule {
    
    @Binds
    abstract fun logic(logic: LogicImpl): Logic
}