package com.degalex.waadsutest.domain.interactors.interfaces

import com.degalex.waadsutest.domain.entities.Island

interface GetIslandLengthInteractor {

    operator fun invoke(island: Island?): Int
}