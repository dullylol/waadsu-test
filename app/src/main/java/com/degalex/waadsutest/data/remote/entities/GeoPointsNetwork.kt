package com.degalex.waadsutest.data.remote.entities

import com.google.gson.annotations.SerializedName

data class GeoPointsNetwork(
    @SerializedName("features") val features: List<FeatureNetwork>?,
)
