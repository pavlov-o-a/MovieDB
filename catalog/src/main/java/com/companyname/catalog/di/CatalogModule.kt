package com.companyname.catalog.di

import com.companyname.catalog.logic.Logic
import com.companyname.catalog.logic.LogicImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CatalogModule {

    @Binds
    abstract fun logic(logicImpl: LogicImpl): Logic
}