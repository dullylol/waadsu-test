package com.degalex.waadsutest.ui.screens

import androidx.appcompat.app.AppCompatActivity
import com.degalex.waadsutest.ui.base.BaseViewModel
import com.degalex.waadsutest.ui.navigation.ActivityHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val activityHolder: ActivityHolder,
    private val mainRouter: MainRouter,
) : BaseViewModel() {

    fun onAttachActivity(activity: AppCompatActivity) {
        activityHolder.attachActivity(activity)
    }

    fun onDetachActivity() {
        activityHolder.detachActivity()
    }

    fun onActivityCreated() {
        // TODO - Implement
    }
}