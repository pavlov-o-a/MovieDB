package com.companyname.repository.net

import com.companyname.common.AppScope
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun getRetrofit(retrofitFactory: RetrofitFactory): RetrofitWrapper {
        return RetrofitWrapper(retrofitFactory.getRetrofit())
    }
}