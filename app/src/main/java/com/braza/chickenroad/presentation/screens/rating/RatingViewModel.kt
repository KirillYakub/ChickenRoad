package com.braza.chickenroad.presentation.screens.rating

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.domain.repository.leaders.GetLeadersRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getLeadersRepos: GetLeadersRepos
) : ViewModel() {

    var leadersList = mutableStateListOf<Pair<String, String>>()
        private set
    var isLoading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            leadersList.addAll(getLeadersRepos.getAllLeaders())
            delay(2000)
            isLoading = false
        }
    }
}