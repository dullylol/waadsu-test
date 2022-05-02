package com.degalex.waadsutest.ui.screens

import com.degalex.waadsutest.R
import com.degalex.waadsutest.ui.base.BaseRouter
import com.degalex.waadsutest.ui.navigation.ActivityHolder
import com.degalex.waadsutest.ui.screens.start.StartFragment
import javax.inject.Inject

class MainRouter @Inject constructor(
    activityHolder: ActivityHolder,
) : BaseRouter(activityHolder) {

    fun openStartScreen() {
        replaceFragment(R.id.activityFragmentContainer, StartFragment.newInstance())
    }
}