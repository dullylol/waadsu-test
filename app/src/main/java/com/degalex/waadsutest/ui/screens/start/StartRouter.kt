package com.degalex.waadsutest.ui.screens.start

import com.degalex.waadsutest.R
import com.degalex.waadsutest.ui.base.BaseRouter
import com.degalex.waadsutest.ui.navigation.ActivityHolder
import com.degalex.waadsutest.ui.screens.map.MapFragment
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class StartRouter @Inject constructor(
    activityHolder: ActivityHolder,
) : BaseRouter(activityHolder) {

    fun openMapScreen() {
        openFragment(R.id.activityFragmentContainer, MapFragment.newInstance())
    }
}