package com.root14.teamup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.teamup.data.repo.TeamRepo
import com.root14.teamup.model.entity.TeamModel
import com.root14.teamup.model.state.TeamUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTeamViewModel @Inject constructor(private val teamRepo: TeamRepo) : ViewModel() {

    private var _deleteTeamUiState = MutableLiveData<TeamUiState>()
    var deleteTeamUiState: LiveData<TeamUiState> = _deleteTeamUiState

    fun deleteTeam(teamName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //SSOT
                teamRepo.getTeam(teamName).let {
                    teamRepo.deleteTeam(it)
                    _deleteTeamUiState.postValue(TeamUiState(isLoading = false))
                }
            } catch (e: Exception) {
                _deleteTeamUiState.postValue(TeamUiState(isLoading = true, exception = e))
            }

        }
    }


    private var _getAllTeam = MutableLiveData<List<TeamModel>?>()
    var getAllTeam: LiveData<List<TeamModel>?> = _getAllTeam

    fun getAllTeam(): LiveData<List<TeamModel>?> {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teamList = teamRepo.getAllTeams()
                _getAllTeam.postValue(teamList)
            } catch (exception: Exception) {
                _getAllTeam.postValue(null)
                exception.printStackTrace()
            }
        }
        return getAllTeam
    }

    private var _getTeamCount = MutableLiveData<Int>()
    var getTeamCount: LiveData<Int> = _getTeamCount
    fun getTeamCount(): LiveData<Int> {
        viewModelScope.launch(Dispatchers.IO) {
            _getTeamCount.postValue(teamRepo.getTeamCount())
        }
        return getTeamCount
    }
}