package com.degalex.waadsutest.ui.screens.map

import com.degalex.waadsutest.ui.base.BaseRouter
import com.degalex.waadsutest.ui.navigation.ActivityHolder
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MapRouter @Inject constructor(
    activityHolder: ActivityHolder,
) : BaseRouter(activityHolder)