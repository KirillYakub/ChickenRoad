package com.braza.chickenroad.presentation.screens.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.domain.repository.FirstInputRepos
import com.braza.chickenroad.domain.repository.leaders.InsertLeaderRepos
import com.braza.chickenroad.util.leadersList
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
    private val insertLeaderRepos: InsertLeaderRepos
) : ViewModel() {

    private val _loadEndState = MutableStateFlow(false)
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
            delay(2000)
            _loadEndState.value = true
        }
    }
}