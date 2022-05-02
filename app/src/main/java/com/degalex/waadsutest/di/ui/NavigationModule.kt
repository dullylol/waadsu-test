package com.degalex.waadsutest.di.ui

import com.degalex.waadsutest.ui.navigation.ActivityHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class NavigationModule {

    @Provides
    @ActivityRetainedScoped
    fun provideActivityHolder() = ActivityHolder()
}