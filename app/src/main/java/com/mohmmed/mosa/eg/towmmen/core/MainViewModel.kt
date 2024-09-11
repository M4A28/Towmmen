package com.mohmmed.mosa.eg.towmmen.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.AppEntryUseCases
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject  constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppNavigation.route)
        private set

    init{
        appEntryUseCases.readAppEntry().onEach { canStartFromHomeScreen ->
            if(canStartFromHomeScreen){
                startDestination = Route.AppNavigation.route
            }else{
                startDestination = Route.AppStartNavigation.route
            }
            delay(250)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}
