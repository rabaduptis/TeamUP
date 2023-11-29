package com.root14.teamup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.model.state.TeamUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateTeamViewModel @Inject constructor(private val prefDataStoreManager: PrefDataStoreManager) :
    ViewModel() {

    private var _teamUiState = MutableLiveData<TeamUiState>()
    var teamUiState: LiveData<TeamUiState> = _teamUiState

    /**
     * The UI state of the team creation process.
     * Saves the team name and description in the data store with "Team" tag.
     * It's a inside operation that this fun is doing for using one PrefDataStore.
     */

    fun createTeam(teamName: String, teamDescription: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val teamUid = UUID.randomUUID().toString()
            try {
                prefDataStoreManager.saveStringData(
                    "${Firebase.auth.currentUser?.displayName}+$teamName",
                    "$teamDescription+$teamUid"
                )
                // Update the UI state with a success message.
                _teamUiState.postValue(TeamUiState(isError = false))
            } catch (exception: Exception) {
                // Update the UI state with an error message and the exception.
                _teamUiState.postValue(
                    TeamUiState(
                        isError = true, exception = exception
                    )
                )
            }

        }
    }
}
