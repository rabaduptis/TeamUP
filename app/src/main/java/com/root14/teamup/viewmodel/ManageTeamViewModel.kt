package com.root14.teamup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.model.state.TeamUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTeamViewModel @Inject constructor(private val prefDataStoreManager: PrefDataStoreManager) :
    ViewModel() {

    private var _deleteTeamUiState = MutableLiveData<TeamUiState>()
    var deleteTeamUiState: LiveData<TeamUiState> = _deleteTeamUiState

    fun deleteTeam(teamName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                prefDataStoreManager.deleteData(teamName)
                _deleteTeamUiState.postValue(TeamUiState(isLoading = false))
            } catch (e: Exception) {
                _deleteTeamUiState.postValue(TeamUiState(isLoading = true, exception = e))
            }

        }
    }
}