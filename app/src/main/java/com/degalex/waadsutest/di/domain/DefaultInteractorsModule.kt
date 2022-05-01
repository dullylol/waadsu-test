package com.degalex.waadsutest.di.domain

import com.degalex.waadsutest.di.utils.annotations.qualifiers.interactors.Default
import com.degalex.waadsutest.domain.interactors.implementation.GetConcatenatedTerritoryInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetIslandLengthInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetTerritoryInteractorDefault
import com.degalex.waadsutest.domain.interactors.implementation.GetTerritoryLengthInteractorDefault
import com.degalex.waadsutest.domain.interactors.interfaces.GetConcatenatedTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetIslandLengthInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryInteractor
import com.degalex.waadsutest.domain.interactors.interfaces.GetTerritoryLengthInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface DefaultInteractorsModule {

    @Binds
    @ViewModelScoped
    @Default
    fun bindGetConcatenatedTerritoryInteractor(
        getConcatenatedTerritoryInteractorDefault: GetConcatenatedTerritoryInteractorDefault,
    ): GetConcatenatedTerritoryInteractor

    @Binds
    @ViewModelScoped
    @Default
    fun bindGetTerritoryInteractor(
        getTerritoryInteractorDefault: GetTerritoryInteractorDefault,
    ): GetTerritoryInteractor

    @Binds
    @ViewModelScoped
    @Default
    fun bindGetTerritoryLengthInteractor(
        getTerritoryLengthInteractorDefault: GetTerritoryLengthInteractorDefault,
    ): GetTerritoryLengthInteractor

    @Binds
    @ViewModelScoped
    @Default
    fun bindGetIslandLengthInteractor(
        getIslandLengthInteractorDefault: GetIslandLengthInteractorDefault,
    ): GetIslandLengthInteractor
}