package com.degalex.waadsutest.data.exceptions

import java.io.IOException

data class NetworkException(
    val code: Int,
) : IOException()