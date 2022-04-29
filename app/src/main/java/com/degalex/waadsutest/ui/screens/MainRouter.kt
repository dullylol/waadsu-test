package com.degalex.waadsutest.ui.screens

import com.degalex.waadsutest.ui.base.BaseRouter
import com.degalex.waadsutest.ui.navigation.ActivityHolder
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MainRouter @Inject constructor(
    activityHolder: ActivityHolder,
) : BaseRouter(activityHolder)