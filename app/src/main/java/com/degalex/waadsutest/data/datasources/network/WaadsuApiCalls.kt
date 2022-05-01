package com.degalex.waadsutest.data.datasources.network

import com.degalex.waadsutest.data.entities.network.GeoPointsNetwork
import retrofit2.http.GET

interface WaadsuApiCalls {

    @GET("russia.geo.json")
    suspend fun getGeoPoints(): GeoPointsNetwork
}