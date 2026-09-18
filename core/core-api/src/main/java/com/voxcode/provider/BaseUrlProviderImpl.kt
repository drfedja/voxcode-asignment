package com.voxcode.provider

import com.voxcode.api.BuildConfig
import javax.inject.Inject

class BaseUrlProviderImpl @Inject constructor() : BaseUrlProvider {
    override fun provideNewsBaseUrl(): String = BuildConfig.BASE_URL
}
