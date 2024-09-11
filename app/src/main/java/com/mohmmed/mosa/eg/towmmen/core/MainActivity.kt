package com.mohmmed.mosa.eg.towmmen.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.work.BackoffPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppTheme
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppThemeViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.NavGraph
import com.mohmmed.mosa.eg.towmmen.ui.theme.DarkColorScheme
import com.mohmmed.mosa.eg.towmmen.ui.theme.LightColorScheme
import com.mohmmed.mosa.eg.towmmen.ui.theme.TowmmenTheme
import com.mohmmed.mosa.eg.towmmen.worker.BackUpDBWork
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val themeViewModel by  viewModels<AppThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        val dbWork = PeriodicWorkRequestBuilder<BackUpDBWork>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS)
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            )
            .build()
        val workManger = WorkManager.getInstance(applicationContext)

        workManger.enqueue(dbWork)

        setContent {
            val theme by themeViewModel.theme.collectAsState()
            val isDarkMode = isSystemInDarkTheme()
            val colorScheme = when (theme) {
                AppTheme.LIGHT -> LightColorScheme
                AppTheme.DARK -> DarkColorScheme
                AppTheme.SYSTEM -> if (isDarkMode) DarkColorScheme else LightColorScheme
            }
            TowmmenTheme(colorScheme = colorScheme){
                val systemUiColor = rememberSystemUiController()
                val surface  = MaterialTheme.colorScheme.surface
                SideEffect {
                    systemUiColor.setSystemBarsColor(
                        color = surface,
                        darkIcons = !isDarkMode
                    )
                }
                val startDestination = viewModel.startDestination
                NavGraph(startDestination = startDestination)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

}
