package com.degalex.waadsutest.di.utils.annotations.qualifiers.dispatchers

import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IODispatcher