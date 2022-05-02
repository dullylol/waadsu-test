package com.degalex.waadsutest.data.entities.network

import com.google.gson.annotations.SerializedName

data class GeoPointsNetwork(
    @SerializedName("features") val features: List<FeatureNetwork>?,
)
