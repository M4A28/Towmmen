package com.mohmmed.mosa.eg.towmmen.presenter.test

import androidx.lifecycle.ViewModel
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val db: TowmmenDatabase
): ViewModel() {

}