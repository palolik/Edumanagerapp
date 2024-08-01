package com.kotlin5.edumanager.presentation.splash.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.text.Typography.dagger

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : ViewModel() {
    val shouldNavigateLiveData by lazy { DataBundleLiveData() }
    fun shouldNavigate() = viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            launch {
                val isViewed = preferenceDataStoreHelper.getFirstPreference(
                    PreferenceDataStoreConstants.IS_WELL_COME_SCREEN_VISIT,
                    false
                )
                shouldNavigateLiveData.postValue(DataResource.success(isViewed))
            }
        }
    fun setWellComeScreenVisit(){
        viewModelScope.launch(Dispatchers.IO) {
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.IS_WELL_COME_SCREEN_VISIT,
                true
            )
        }
    }
}