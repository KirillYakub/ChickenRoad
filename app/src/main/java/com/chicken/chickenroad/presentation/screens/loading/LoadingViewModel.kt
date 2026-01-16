package com.chicken.chickenroad.presentation.screens.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.chickenroad.domain.repository.FirstInputRepos
import com.chicken.chickenroad.domain.repository.leaders.InsertLeaderRepos
import com.chicken.chickenroad.presentation.screens.loading.components.LoadState
import com.chicken.chickenroad.util.CheckDebugEnable
import com.chicken.chickenroad.util.leadersList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val firstInputRepos: FirstInputRepos,
    private val insertLeaderRepos: InsertLeaderRepos,
    private val checkDebugEnable: CheckDebugEnable
) : ViewModel() {

    private val _loadEndState = MutableStateFlow(LoadState())
    val loadEndState = _loadEndState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isFirstInput = firstInputRepos.readFirstInput()
            if (isFirstInput) {
                leadersList.forEach { leader ->
                    insertLeaderRepos.insertLeader(leader)
                }
                firstInputRepos.saveFirstInput(false)
            }
            openNecessaryPage()
        }
    }

    private suspend fun openNecessaryPage() {
        if (checkDebugEnable.isUsbDebuggingOff()) {
            _loadEndState.value = LoadState(openWebView = true)
        }
        else {
            delay(2000)
            _loadEndState.value = LoadState(openGame = true)
        }
    }
}