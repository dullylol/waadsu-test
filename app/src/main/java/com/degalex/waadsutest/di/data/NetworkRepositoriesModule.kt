package com.degalex.waadsutest.di.data

import com.degalex.waadsutest.data.repositories.interfaces.CoordinatesRepository
import com.degalex.waadsutest.data.repositories.network.CoordinatesRepositoryNetwork
import com.degalex.waadsutest.di.utils.annotations.qualifiers.repositories.Network
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkRepositoriesModule {

    @Binds
    @Singleton
    @Network
    fun bindsCoordinatesRepositoryNetwork(
        coordinatesRepositoryNetwork: CoordinatesRepositoryNetwork,
    ): CoordinatesRepository
}