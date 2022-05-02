package com.degalex.waadsutest.data.repositories.network

import com.degalex.waadsutest.data.datasources.network.WaadsuApiCalls
import com.degalex.waadsutest.data.repositories.interfaces.CoordinatesRepository
import javax.inject.Inject

class CoordinatesRepositoryNetwork @Inject constructor(
    private val waadsuApiCalls: WaadsuApiCalls,
) : CoordinatesRepository {

    override suspend fun getCoordinates(): List<List<List<List<Double>>>> {
        return waadsuApiCalls.getGeoPoints().features?.firstOrNull()?.geometry?.coordinates
            ?: emptyList()
    }
}