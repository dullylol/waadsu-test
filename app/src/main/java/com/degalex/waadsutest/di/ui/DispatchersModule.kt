package com.degalex.waadsutest.di.ui

import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.DefaultDispatcher
import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.IODispatcher
import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.MainDispatcher
import com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class DispatchersModule {

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @IODispatcher
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @UnconfinedDispatcher
    fun provideUnconfinedDispatcher() = Dispatchers.Unconfined

    @Provides
    @MainDispatcher
    fun provideMainDispatcher() = Dispatchers.Main
}