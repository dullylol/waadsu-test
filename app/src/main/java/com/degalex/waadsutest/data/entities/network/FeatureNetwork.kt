package com.degalex.waadsutest.data.entities.network

import com.google.gson.annotations.SerializedName

data class FeatureNetwork(
    @SerializedName("geometry") val geometry: GeometryNetwork?,
)
