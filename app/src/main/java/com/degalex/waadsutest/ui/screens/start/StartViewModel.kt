package com.degalex.waadsutest.ui.screens.start

import com.degalex.waadsutest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startRouter: StartRouter,
) : BaseViewModel() {

    fun onOpenMapClicked() {
        startRouter.openMapScreen()
    }
}