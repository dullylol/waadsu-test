package com.degalex.waadsutest.data.remote.repositories

import com.degalex.waadsutest.data.remote.datasources.WaadsuApiCalls
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoordinatesRepository @Inject constructor(
    private val waadsuApiCalls: WaadsuApiCalls,
) {

    suspend fun getCoordinates(): List<List<List<List<Double>>>> {
        return waadsuApiCalls.getGeoPoints().features?.firstOrNull()?.geometry?.coordinates
            ?: emptyList()
    }
}