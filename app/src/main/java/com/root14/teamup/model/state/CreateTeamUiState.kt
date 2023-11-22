package com.root14.teamup.model.state

import java.lang.Exception

data class CreateTeamUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = true,
    val exception: Exception? = null
)